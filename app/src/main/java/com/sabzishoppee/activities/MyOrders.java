package com.sabzishoppee.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.sabzishoppee.R;

import Controls.InternetCheck;
import Controls.OrderAsync;
import Controls.Session;

/**
 * Created by JAY JADIA.
 */

public class MyOrders extends AppCompatActivity {

    Session session;
    RecyclerView recyclerView;
    InternetCheck internetCheck;

    public static final String ORDERS_WS_URL = "YOUR WS URL";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_orders);

        recyclerView = findViewById(R.id.order_recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new DividerItemDecoration(this,LinearLayoutManager.VERTICAL));
        session = new Session(this);

        internetCheck= new InternetCheck(this);
        if (internetCheck.isInternetOn()) {
            OrderAsync async = new OrderAsync(ORDERS_WS_URL + "?user_id=" + session.getUserId(), this, "myOrders", recyclerView);
            async.execute();
        } else {
            Toast.makeText(this, "Please Check your Internet Connection", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
