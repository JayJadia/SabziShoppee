package com.sabzishoppee.activities;

import android.app.Activity;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
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
 * Created by JAY JADIA.
 */

public class FruitFragment extends android.support.v4.app.Fragment
{
    RecyclerView recyclerView;

    Session session;
    DataParser dataParser;
    SearchView searchView;
    int count = 0;
    DatabaseHelper helper;
    TextView totalCost;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_fruits,container,false);
        dataParser = new DataParser(this.getContext());
        totalCost = view.findViewById(R.id.total_cost_text);
        session = new Session(this.getContext());
        helper = new DatabaseHelper(this.getContext());
        recyclerView = view.findViewById(R.id.fruits_recycler);
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
       /* View cartCount = menu.findItem(R.id.cart).getActionView();
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
        });
*/
        // Associate searchable configuration with the SearchView
        //SearchManager searchManager = (SearchManager) getContext().getSystemService(Context.SEARCH_SERVICE);
        searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
        //searchView.setSearchableInfo(searchManager.getSearchableInfo(getActivity().getComponentName()));
        searchView.setMaxWidth(Integer.MAX_VALUE);
        if (count == 0) {
            GetProductData data = new GetProductData(this.getContext(), "WS_URL", "fruit", recyclerView, searchView, totalCost);
            data.execute();
            count = 1;
        }
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