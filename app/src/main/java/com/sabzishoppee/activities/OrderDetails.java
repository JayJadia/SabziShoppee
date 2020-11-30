package com.sabzishoppee.activities;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.sabzishoppee.R;

import Controls.Session;

/**
 * Created by JAY JADIA.
 */
public class OrderDetails extends AppCompatActivity {


    TableLayout orderInvoiceTable;
    int orderId,orderUserId;
    String orderNumber,productIds,rates,quantities,productName;

    TextView userName, deliveryAdddress, totalCost, ordernumber;
    Session session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_details);
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        session  = new Session(this);

        orderInvoiceTable = findViewById(R.id.invoice_table);
        userName = findViewById(R.id.userName);
        deliveryAdddress =findViewById(R.id.deliveryAddress);
        ordernumber = findViewById(R.id.order_number);

        userName.setText("Order Placed by : "+session.getUserName());
        deliveryAdddress.setText("Delivery Address : "+session.getAddress());

        totalCost = findViewById(R.id.totalCost);
        totalCost.setText("Total Cost : ₹ "+bundle.getInt("total_cost",0));

        ordernumber.setText("Order Number : "+ bundle.getString("order_number",null));

        //table Header
        TableRow tableHead = new TableRow(this);
        tableHead.setBackgroundColor(getResources().getColor(R.color.colorPrimary));

        tableHead.setLayoutParams(new TableLayout.LayoutParams(
                TableLayout.LayoutParams.MATCH_PARENT,
                TableLayout.LayoutParams.MATCH_PARENT));

        TextView productId = new TextView(this);
        productId.setText("Products");
        productId.setPadding(5,5,5,5);
        productId.setGravity(Gravity.CENTER);
        productId.setTextSize(20);
        productId.setTextColor(getResources().getColor(R.color.backgroundColor));
        tableHead.addView(productId);

        TextView rate = new TextView(this);
        rate.setText("Rate");
        rate.setGravity(Gravity.CENTER);
        rate.setTextSize(20);
        rate.setPadding(5,5,5,5);
        rate.setTextColor(getResources().getColor(R.color.backgroundColor));
        tableHead.addView(rate);

        TextView quantity = new TextView(this);
        quantity.setText("Quantity");
        quantity.setGravity(Gravity.CENTER);
        quantity.setTextSize(20);
        quantity.setTextColor(getResources().getColor(R.color.backgroundColor));
        quantity.setPadding(5,5,5,5);
        tableHead.addView(quantity);


        orderInvoiceTable.addView(tableHead, new TableLayout.LayoutParams(
                TableLayout.LayoutParams.MATCH_PARENT,
                TableLayout.LayoutParams.MATCH_PARENT));

        createTable(bundle,this);
    }

    public void createTable(Bundle bundle, Context context)
    {
        orderId = bundle.getInt("order_id",0);
        orderUserId = bundle.getInt("order_user_id",0);
        orderNumber = bundle.getString("order_number",null);
        productIds = bundle.getString("product_id_array",null);
        rates = bundle.getString("product_rate_array",null);
        quantities = bundle.getString("product_quantity_array",null);
        productName = bundle.getString("product_name_array",null);


        String[] idArray = productIds.split(",");
        String[] rateArray = rates.split(",");
        String[] quantityArray = quantities.split(",");
        String[] nameArray = productName.split(",");


        for(int i =0;i<quantityArray.length;i++)
        {
            TableRow row = new TableRow(context);
            row.setLayoutParams(new TableLayout.LayoutParams(
                    TableLayout.LayoutParams.MATCH_PARENT,
                    TableLayout.LayoutParams.MATCH_PARENT));
            row.setBackground(getResources().getDrawable(R.drawable.cell_back));


            TextView productId = new TextView(context);
            productId.setText(nameArray[i]);
            productId.setGravity(Gravity.CENTER);
            productId.setTextSize(20);
            productId.setPadding(5, 5, 5, 5);
            productId.setTextColor(getResources().getColor(R.color.text));
            row.addView(productId);

            TextView rate = new TextView(context);
            rate.setText(rateArray[i]);
            rate.setGravity(Gravity.CENTER);
            rate.setTextSize(20);
            rate.setPadding(5, 5, 5, 5);
            rate.setTextColor(getResources().getColor(R.color.text));
            row.addView(rate);

            TextView quantity = new TextView(context);
            quantity.setText(quantityArray[i]);
            quantity.setTextSize(20);
            quantity.setTextColor(getResources().getColor(R.color.text));
            quantity.setGravity(Gravity.CENTER);
            quantity.setPadding(5, 5, 5, 5);
            row.addView(quantity);

            orderInvoiceTable.addView(row, new TableLayout.LayoutParams(
                    TableLayout.LayoutParams.MATCH_PARENT,
                    TableLayout.LayoutParams.MATCH_PARENT));
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
