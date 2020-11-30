package Controls;

import android.app.SearchManager;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SearchView;
import android.widget.TextClock;
import android.widget.TextView;
import android.widget.Toast;

import com.sabzishoppee.R;
import com.sabzishoppee.activities.Home;
import com.sabzishoppee.activities.VegetableFragment;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;

import java.util.ArrayList;

import model.CartModel;
import model.ProductModel;

/**
 * Created by JAY JADIA.
 */

public class ProductRecyclerAdapter extends RecyclerView.Adapter<ProductRecyclerAdapter.ViewHolder> {

    ArrayList<ProductModel> productModelList;
    ArrayList<ArrayList<String>> list;
    ArrayList<String> listItem;
    Context context;
    String category;
    ProductModel productModel;
    JSONArray dataArray;
    Session session;

    DatabaseHelper dbhelper;

    Home home;
    TextView totalCost;
    ArrayList<ProductModel> filteredProducts;



    public ProductRecyclerAdapter(ArrayList<ProductModel> productModelList,
                                  Context context, TextView totalCost)
    {
        this.context = context;
        this.productModelList = productModelList;
        dataArray = new JSONArray();
        session = new Session(context);
        dbhelper= new DatabaseHelper(context);
        home = new Home();
        this.totalCost = totalCost;
        list = new ArrayList<>();
        updateCost();
    }

    @Override
    public ProductRecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.recycler_item,parent,false));
    }

    @Override
    public void onBindViewHolder(final ProductRecyclerAdapter.ViewHolder holder, final int position)
    {
        final int[] count = new int[1];
         productModel = productModelList.get(position);
         Log.d("POSITION",position+"\n");

            holder.productName.setText(productModel.getTitle());
            holder.productRate.setText("₹ "+productModel.getRate());
            holder.productRateType.setText("/"+productModel.getRateType());
            holder.minimumQuantity.setText(productModel.getMinQuantity());
            holder.boughtQuantity.setText("0");

            final int[] q = {Integer.parseInt(holder.boughtQuantity.getText().toString())};
        final int min = Integer.parseInt(holder.minimumQuantity.getText().toString());


        count[0] = 0;

            holder.addProduct.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (count[0] == 0) {
                        holder.boughtQuantity.setText(q[0] + min + "");
                        holder.removeProduct.setEnabled(true);
                        addToCart(position, holder);
                        q[0]++;
                        count[0] = 1;
                    } else if (count[0] == 1) {
                        holder.boughtQuantity.setText(q[0] + min + "");
                        holder.removeProduct.setEnabled(true);
                        addToCart(position, holder);
                        q[0]++;
                    }
                   /* cost=add*(Integer.parseInt(productModel.getRate()));
                    if(session.getCost()!=0)
                    {
                        session.setCost(cost+session.getCost());
                        totalCost.setText("Total Cost : ₹ "+session.getCost());
                    }*/
                }
            });



            holder.removeProduct.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (q[0] <= min)
                    {
                        Toast.makeText(context, "Minimum quantity could not be reduced", Toast.LENGTH_SHORT).show();
                        holder.removeProduct.setEnabled(false);
                    }
                    else
                    {
                        q[0]--;
                        holder.boughtQuantity.setText(q[0]+"");
                        addToCart(position,holder);
                    }

                }
            });

        if(!productModel.getImageUrl().equals(""))
        {
            Picasso.with(context).load(productModel.getImageUrl()).into(holder.productImage);
        }
        else
        {
            holder.productImage.setImageResource(android.R.drawable.ic_menu_search);
        }
    }

    @Override
    public int getItemCount() {
        return productModelList.size();
    }

    public void addToCart(int position, ViewHolder holder)
    {
        productModel = productModelList.get(position);
        int quant = Integer.parseInt(holder.boughtQuantity.getText().toString());
        int rate = Integer.parseInt(productModel.getRate());

        //inserting to SQLliteDatabse
        dbhelper.insertProduct(Integer.parseInt(productModel.getId()),
                productModel.getTitle(),rate,
                quant,Integer.parseInt(productModel.getMinQuantity()),
                productModel.getRateType(),productModel.getProductCategory(),
                productModel.getImageUrl());

        totalCost.setText("Total Cost : ₹ " + updateCost());

    }

    public int updateCost() {
        int cost = 0;
        list = dbhelper.readProducts();
        listItem = new ArrayList<>();

        if (list.size() == 0) {
            session.setCost(0);
            return 0;
        } else {
            for (int i = 0; i < list.size(); i++) {
                listItem = list.get(i);
                cost += (Integer.parseInt(listItem.get(2))) * (Integer.parseInt(listItem.get(5)));
            }
            session.setCost(cost);
            return cost;
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView productName, productRate, productRateType, minimumQuantity, boughtQuantity;
        ImageButton addProduct, removeProduct;
        ImageView productImage;

        LinearLayout layout;

        public ViewHolder(View itemView) {
            super(itemView);

            productName = itemView.findViewById(R.id.product_name);
            productRate = itemView.findViewById(R.id.product_rate);
            productRateType = itemView.findViewById(R.id.product_rate_type);
            minimumQuantity = itemView.findViewById(R.id.minimum_quantity);
            boughtQuantity = itemView.findViewById(R.id.quantity_text);

            layout = itemView.findViewById(R.id.recycler_item_layout);

            addProduct = itemView.findViewById(R.id.increase_quantity);
            removeProduct = itemView.findViewById(R.id.decrease_quantity);
            productImage = itemView.findViewById(R.id.product_image);
        }
    }
}

