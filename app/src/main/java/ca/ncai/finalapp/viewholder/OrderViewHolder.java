package ca.ncai.finalapp.viewholder;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import ca.ncai.finalapp.R;
import ca.ncai.finalapp.models.OrderObject;

/**
 * Created by niang on 12/6/2017.
 */

public class OrderViewHolder extends RecyclerView.ViewHolder {
    private static final String TAG = "OrderViewHolder";
    private final TextView username;
    private final TextView dealname;
    private final TextView quantity;
    private final TextView unitprice;
    private final TextView total;
    private final TextView paid;
    private final TextView shipped;

    public OrderViewHolder(ViewGroup group) {
        super(LayoutInflater.from(group.getContext()).inflate(R.layout.order_entry, group, false));

        Log.d(TAG, "this DealViewHolder");
        username = (TextView) itemView.findViewById(R.id.order_user_name);
        dealname = (TextView) itemView.findViewById(R.id.order_deal_name);
        quantity = (TextView) itemView.findViewById(R.id.order_quantity);
        unitprice = (TextView) itemView.findViewById(R.id.order_unit_price);
        total = (TextView) itemView.findViewById(R.id.order_total);
        paid = (TextView) itemView.findViewById(R.id.order_paid);
        shipped = (TextView) itemView.findViewById(R.id.order_shipped);

    }

    public void bind(OrderObject orderObject){
        username.setText(orderObject.username);
        dealname.setText(orderObject.dealtitle);
        quantity.setText(orderObject.quantity);
        unitprice.setText(orderObject.unitprice);
        total.setText(orderObject.total());
        paid.setText(orderObject.paid == "1" ? "Paid" : "unPaid");
        shipped.setText(orderObject.shipped == "0" ? "Ready to ship" : "Shiped");

    }
}
