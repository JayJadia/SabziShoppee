package com.sabzishoppee.activities;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.sabzishoppee.R;
import java.util.ArrayList;
import Controls.DataParser;
import Controls.DatabaseHelper;
import Controls.GetProductData;
import Controls.ProductRecyclerAdapter;
import Controls.Session;
import model.ProductModel;

/**
 * Created by RAJAT JADIA on 25-06-2018.
 */

public class VegetableFragment extends android.support.v4.app.Fragment
{

    public static RecyclerView recyclerView;
    ProductRecyclerAdapter adapter;
    ArrayList<ProductModel> productModelList;

    Session session;
    DataParser dataParser;
    ProductModel model;
    SearchView searchView;

    DatabaseHelper helper;

    TextView totalCost;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_vegetables,container,false);

        model = new ProductModel();
        session = new Session(this.getContext());
        helper = new DatabaseHelper(this.getContext());
        dataParser = new DataParser(this.getContext());
        recyclerView = view.findViewById(R.id.veg_recycler);
        totalCost = view.findViewById(R.id.total_cost_text);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
        recyclerView.addItemDecoration(new DividerItemDecoration(this.getContext(),LinearLayoutManager.VERTICAL));

        totalCost.setText("Total Cost : ₹" + session.getCost());
        totalCost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), Cart.class));
            }
        });
        setHasOptionsMenu(true);
        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu, menu);
        /*View cartCount = menu.findItem(R.id.cart).getActionView();
        TextView textView = cartCount.findViewById(R.id.cart_textview);
        textView.setText(helper.readProducts().size()+"");
        cartCount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(),Cart.class));
            }
        });
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(),Cart.class));
            }
        });*/

        searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
        searchView.setMaxWidth(Integer.MAX_VALUE);

        GetProductData data = new GetProductData(this.getContext(), "WEB_SVC_URL", "vegetable", recyclerView, searchView, totalCost);
        data.execute();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.cart) {
            startActivity(new Intent(this.getContext(), Cart.class));
        } else if (item.getItemId() == R.id.logout) {
            startActivity(new Intent(this.getContext(), Login.class));
            session.logoutUser();

            getActivity().finish();

        } else if (item.getItemId() == R.id.my_orders) {
            startActivity(new Intent(this.getContext(), MyOrders.class));
        }
        return true;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }
}
