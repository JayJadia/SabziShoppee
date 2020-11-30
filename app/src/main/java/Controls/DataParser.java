package Controls;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.sabzishoppee.R;
import com.sabzishoppee.activities.Home;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import model.OrderModel;
import model.ProductModel;

/**
 * Created by JAY JADIA.
 */

public class DataParser
{

    String parsedData, category;
    DatabaseHelper helper;
    Context context;
    Session session;
    String SERVER_URL = "YOUR SERVER URL";
    ProductRecyclerAdapter vegAdapter, fruitAdapter;
    ArrayList<ProductModel> productModelList;
    ArrayList<ProductModel> filteredProducts;

    RecyclerView recyclerView;

    public DataParser(Context context)
    {
        this.context = context;
        session = new Session(context);
        helper = new DatabaseHelper(context);
    }
    String parseLoginResponse(String responseData)
    {
        JSONObject object;
        try {
            object = new JSONObject(responseData);
            parsedData = object.getString("message");
            JSONObject userDetails = object.getJSONObject("user");
            session.addUser(userDetails.getInt("user_id"),
                    userDetails.getString("full_name"),
                    userDetails.getString("address"));

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return parsedData;
    }

    public ArrayList<ProductModel> parseProductResponse(String responseData, final RecyclerView recyclerView,
                                                        final String category, android.support.v7.widget.SearchView searchView, final TextView totalCost)
    {
        JSONObject object;
        JSONArray jsonArray;
        this.category = category;
        productModelList = new ArrayList<>();
        this.recyclerView = recyclerView;

        try {
            object = new JSONObject(responseData);
            jsonArray = object.getJSONArray("product");

            if(jsonArray.length()!=0)
            {
                for(int i=0;i<jsonArray.length();i++)
                {
                    ProductModel productModel = new ProductModel();

                    productModel.setId(jsonArray.getJSONObject(i).getString("product_id"));
                    productModel.setTitle(jsonArray.getJSONObject(i).getString("product_title"));
                    productModel.setRate(jsonArray.getJSONObject(i).getString("product_rate"));
                    productModel.setProductCategory(jsonArray.getJSONObject(i).getString("product_type"));
                    productModel.setRateType((jsonArray.getJSONObject(i).getString("product_rate_type")));
                    productModel.setMinQuantity(jsonArray.getJSONObject(i).getString("product_minimun_quantity"));
                    productModel.setImageUrl(SERVER_URL+(jsonArray.getJSONObject(i).getString("product_image").
                            replace("\\","")));

                    if(category.equalsIgnoreCase(productModel.getProductCategory()))
                    {
                        productModelList.add(productModel);
                    }
                }

                if (productModelList.size() > 0 && (category.equalsIgnoreCase("fruit")))
                {
                    fruitAdapter = new ProductRecyclerAdapter(productModelList, context, totalCost);
                    recyclerView.setAdapter(fruitAdapter);
                    searchView.setOnQueryTextListener(new android.support.v7.widget.SearchView.OnQueryTextListener() {
                        @Override
                        public boolean onQueryTextSubmit(String query) {
                            ArrayList<ProductModel> filterlist = new ArrayList<>();

                            if (query.equals("")) {
                                filterlist.addAll(productModelList);
                            } else {
                                for (ProductModel product : productModelList) {
                                    if (product.getTitle().equalsIgnoreCase(query)) {
                                        filterlist.add(product);
                                    }
                                }
                            }
                            Log.wtf("filterFruit", filterlist.toString());
                            setFilter(filterlist, "fruit", totalCost);
                            return true;
                        }

                        @Override
                        public boolean onQueryTextChange(String newText) {
                            ArrayList<ProductModel> filterlist = new ArrayList<>();
                            if (newText.equals("")) {
                                filterlist.addAll(productModelList);
                            } else {
                                for (ProductModel product : productModelList) {
                                    if (product.getTitle().toLowerCase().contains(newText)) {
                                        filterlist.add(product);
                                    }
                                }
                            }
                            setFilter(filterlist, "fruit", totalCost);
                            return true;
                        }
                    });
                } else if (productModelList.size() > 0 && category.equalsIgnoreCase("vegetable")) {
                    vegAdapter = new ProductRecyclerAdapter(productModelList, context, totalCost);
                    recyclerView.setAdapter(vegAdapter);
                    searchView.setOnQueryTextListener(new android.support.v7.widget.SearchView.OnQueryTextListener() {
                        @Override
                        public boolean onQueryTextSubmit(String query) {
                            ArrayList<ProductModel> filterlist = new ArrayList<>();

                            if (query.isEmpty()) {
                                filterlist.addAll(productModelList);
                            } else {
                                for (ProductModel product : productModelList) {
                                    if (product.getTitle().equalsIgnoreCase(query)) {
                                        filterlist.add(product);
                                    }
                                }
                            }
                            setFilter(filterlist, "vegetable", totalCost);
                            return true;
                        }

                        @Override
                        public boolean onQueryTextChange(String newText) {

                            ArrayList<ProductModel> filterlist = new ArrayList<>();

                            if (newText.isEmpty()) {
                                filterlist.addAll(productModelList);
                            } else {
                                for (ProductModel product : productModelList) {
                                    if (product.getTitle().toLowerCase().contains(newText)) {
                                        filterlist.add(product);
                                    }
                                }
                            }
                            setFilter(filterlist, "vegetable", totalCost);
                            return true;
                        }
                    });
                }
                else {
                    Toast.makeText(context, "No products available", Toast.LENGTH_SHORT).show();
                }

            }

        } catch (JSONException e) {
            e.printStackTrace();
        }


        return productModelList;
    }

    public ArrayList<OrderModel> parseOrderResponse(String jsonResponse,String tag,RecyclerView recyclerView)
    {
        ArrayList<OrderModel> orderList = new ArrayList<>();
        try {
            JSONObject object = new JSONObject(jsonResponse);
            String m = object.getString("message");

            Log.wtf("ORDERMSG", m);
            JSONArray jsonArray = object.getJSONArray("order");

            for (int i = 0; i < jsonArray.length(); i++)
            {
                JSONObject dataObject = jsonArray.getJSONObject(i);
                OrderModel order = new OrderModel();

                order.setOrderId(dataObject.getInt("order_id"));
                order.setOrderNumber(dataObject.getString("order_number"));
                order.setOrderUserId(dataObject.getInt("order_user_id"));
                order.setCost(dataObject.getInt("order_cost"));
                order.setProductIdArray(dataObject.getString("product_id_array"));
                order.setProductRateArray(dataObject.getString("product_rate_array"));
                order.setProductQuantArray(dataObject.getString("product_quantity_array"));
                order.setStatus(dataObject.getString("order_status"));
                order.setProductNameArray(dataObject.getString("product_name_array"));


                orderList.add(order);
            }
            if(tag.equalsIgnoreCase("placeOrder"))
            {
                Intent intent = new Intent(context,Home.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                Toast.makeText(context, "Order Placed", Toast.LENGTH_SHORT).show();
                context.startActivity(intent);
            }

            else if(tag.equalsIgnoreCase("myOrders"))
            {
                OrderRecyclerAdapter adapter = new OrderRecyclerAdapter(context,orderList);
                recyclerView.setAdapter(adapter);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return orderList;
    }


    void setFilter(ArrayList<ProductModel> filteredProductsList, String tag, TextView totalCost) {
        productModelList = new ArrayList<>();
        productModelList.addAll(filteredProductsList);
        if (tag.equalsIgnoreCase("vegetable")) {
            vegAdapter = new ProductRecyclerAdapter(filteredProductsList, context, totalCost);
            recyclerView.setAdapter(vegAdapter);
            //vegAdapter.notifyDataSetChanged();
        } else if (tag.equalsIgnoreCase("fruit")) {
            fruitAdapter = new ProductRecyclerAdapter(filteredProductsList, context, totalCost);
            recyclerView.setAdapter(fruitAdapter);
//            fruitAdapter.notifyDataSetChanged();

        }
    }

}
