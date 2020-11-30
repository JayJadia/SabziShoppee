package com.sabzishoppee.activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.sabzishoppee.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.ArrayList;

import Controls.CartRecyclerAdapter;
import Controls.DatabaseHelper;
import Controls.OrderAsync;
import Controls.Session;
import model.CartModel;

public class Cart extends AppCompatActivity {

    RecyclerView cartRecycler;
    Button placeOrder;
    CartRecyclerAdapter adapter;

    Session session;
    TextView emptyCart;
    DatabaseHelper helper;
    int cost=0;
    ArrayList<Integer> rate,quantity,productId;
    ArrayList<String> productName;
    ArrayList<ArrayList<String>> list;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        emptyCart = findViewById(R.id.empty_cart_msg);
        cartRecycler = findViewById(R.id.cart_recycler);
        placeOrder = findViewById(R.id.place_order);
        adapter = new CartRecyclerAdapter(this, placeOrder, emptyCart);
        cartRecycler.setLayoutManager(new LinearLayoutManager(this));
        cartRecycler.addItemDecoration(new DividerItemDecoration(this,LinearLayoutManager.VERTICAL));

        session = new Session(this);
        helper = new DatabaseHelper(this);

        rate = new ArrayList<>();
        productId = new ArrayList<>();
        quantity = new ArrayList<>();
        productName = new ArrayList<>();

        list = helper.readProducts();
        if(list.size()==0)
        {
            emptyCart.setVisibility(View.VISIBLE);
            placeOrder.setVisibility(View.INVISIBLE);
        }
        else
        {
            cartRecycler.setAdapter(adapter);
            emptyCart.setVisibility(View.INVISIBLE);
            placeOrder.setVisibility(View.VISIBLE);
        }

        ArrayList<String> listItem = new ArrayList<>();
        for (int j = 0; j < list.size(); j++) {
            listItem = list.get(j);
            cost += (Integer.parseInt(listItem.get(2))) * (Integer.parseInt(listItem.get(5)));
            rate.add(j, Integer.parseInt(listItem.get(2)));
            productId.add(j, Integer.parseInt(listItem.get(0)));
            productName.add(j, listItem.get(1));

            quantity.add(j, Integer.parseInt(listItem.get(5)));
        }



        placeOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                new AlertDialog.Builder(Cart.this)
                        .setTitle("Confirm Order")
                        .setMessage("Delivery Address  : "+session.getAddress()+"\n Total Cost : "+cost)
                        .setPositiveButton("Confirm Order", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                                final String suffix = "WS_URL" + "?order_user_id=" + session.getUserId()
                                        + "&order_cost=" + cost + "&product_id_array=" + formatString(productId.toString()) +
                                        "&product_rate_array=" + formatString(rate.toString()) + "&product_quantity_array=" + formatString(quantity.toString()
                                        + "&product_name_array=" + formatString(productName.toString()));

                                OrderAsync async = new OrderAsync(suffix,getApplicationContext(),"placeOrder",null);
                                async.execute();
                                helper.deleteAll();
                                session.setCost(0);
                            }
                        })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                            }
                        })
                        .show();

            }
        });
    }

    String formatString(String string)
    {
        String r =string.replace("]","").replace("[","");
        return  r.replace(" ","");
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(Cart.this, Home.class));
        finish();
    }
}
