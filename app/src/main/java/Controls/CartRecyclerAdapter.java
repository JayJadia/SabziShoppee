package Controls;

import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import com.sabzishoppee.R;
import com.squareup.picasso.Picasso;
import java.util.ArrayList;

/**
 * Created by JAY JADIA.
 */

public class CartRecyclerAdapter extends RecyclerView.Adapter<CartRecyclerAdapter.ViewHolder>{

    Context context;

    DatabaseHelper dbhelper;

    Button placeOrder;
    TextView emptyCart;
    ArrayList<ArrayList<String >> productList;

    public CartRecyclerAdapter(Context context, Button placeOrder, TextView emptyCart)
    {
        this.context=context;
        dbhelper = new DatabaseHelper(context);
        productList = dbhelper.readProducts();
        this.placeOrder = placeOrder;
        this.emptyCart = emptyCart;
    }

    @Override
    public CartRecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.
                from(context).inflate(R.layout.cart_recycelr_item,parent,false));
    }

    @Override
    public void onBindViewHolder(final CartRecyclerAdapter.ViewHolder holder, final int position) {

        //reading from SQLiteDB



        final ArrayList<String> product;

        Log.d("productList",productList.size()+"");

            product = productList.get(position);

            final int id = Integer.parseInt(product.get(0));
            holder.productName.setText(product.get(1));
            int r= Integer.parseInt(product.get(2));
            holder.productRateType.setText(product.get(3));
            final int q= Integer.parseInt(product.get(5));


            holder.productCost.setText((r*q)+"");
            holder.boughtQuantity.setText(q+"");
            holder.productRate.setText(r+"");

        if(!product.get(7).equals(""))
        {
            Picasso.with(context).load(product.get(7)).into(holder.productImage);
        }
        else
        {
            holder.productImage.setImageResource(android.R.drawable.ic_menu_search);
        }


        holder.removeProduct.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View view) {
                dbhelper.deleteItem(id,product.get(7),q);
                productList.remove(position);
                notifyDataSetChanged();
                if (productList.size() == 0) {
                    placeOrder.setVisibility(View.INVISIBLE);
                    emptyCart.setVisibility(View.VISIBLE);
                }

            }
        });


    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder
    {
        TextView productName,productRate,productRateType,boughtQuantity,productCost;
        ImageButton removeProduct;
        ImageView productImage;
        public ViewHolder(View itemView) {
            super(itemView);

            productName = itemView.findViewById(R.id.cart_product_name);
            productRate=itemView.findViewById(R.id.cart_product_rate);
            productRateType=itemView.findViewById(R.id.cart_rate_type);
            boughtQuantity=itemView.findViewById(R.id.product_quantity);
            productCost=itemView.findViewById(R.id.product_cost);

            productImage = itemView.findViewById(R.id.product_image);
            removeProduct = itemView.findViewById(R.id.remove_from_cart);

        }
    }
}
