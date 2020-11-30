package Controls;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sabzishoppee.R;
import com.sabzishoppee.activities.OrderDetails;

import java.util.ArrayList;

import model.OrderModel;


/**
 * Created by JAY JADIA.
 */

public class OrderRecyclerAdapter extends RecyclerView.Adapter<OrderRecyclerAdapter.ViewHolder>
{
    Context context;
    ArrayList<OrderModel> orderList;

    public OrderRecyclerAdapter(Context context,ArrayList<OrderModel> orderList)
    {
        this.context = context;
        this.orderList = orderList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.order_reycler_item,parent,false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position)
    {
        final OrderModel order = orderList.get(position);

        holder.totalCost.setText("₹ "+order.getCost());
        holder.orderNumber.setText("Order Number : "+order.getOrderNumber());
        holder.orderStatus.setText("Order Status : "+order.getStatus());


        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context,OrderDetails.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                Bundle bundle = new Bundle();
                bundle.putInt("order_id",order.getOrderId());
                bundle.putString("order_number",order.getOrderNumber());
                bundle.putInt("total_cost",order.getCost());
                bundle.putString("product_id_array",order.getProductIdArray());
                bundle.putString("product_rate_array",order.getProductRateArray());
                bundle.putString("product_quantity_array",order.getProductQuantArray());
                bundle.putString("product_name_array",order.getProductNameArray());

                intent.putExtras(bundle);
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return orderList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {
        TextView orderNumber,orderStatus,totalCost;
        LinearLayout layout;
        public ViewHolder(View itemView) {
            super(itemView);
            orderNumber = itemView.findViewById(R.id.order_number);
            orderStatus = itemView.findViewById(R.id.order_status);
            totalCost = itemView.findViewById(R.id.order_cost);
            layout = itemView.findViewById(R.id.order_layout);
        }
    }
}
