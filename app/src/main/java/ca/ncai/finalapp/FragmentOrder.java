package ca.ncai.finalapp;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import ca.ncai.finalapp.models.Deal;
import ca.ncai.finalapp.models.Order;
import ca.ncai.finalapp.models.OrderObject;
import ca.ncai.finalapp.models.User;
import ca.ncai.finalapp.viewholder.OrderViewHolder;

import static java.lang.System.in;

/**
 * Created by niang on 12/3/2017.
 */

public class FragmentOrder extends Fragment {
    private static final String TAG = "FragmentOrder";
    final ArrayList<OrderObject> orders = new ArrayList<>();
    DatabaseReference orderRef;
    DatabaseReference userRef;
    DatabaseReference dealRef;

    private OrderAdapter adapter;

    private static final class OrderAdapter extends RecyclerView.Adapter<OrderViewHolder> {
        private List<OrderObject> orders;


        OrderAdapter(List<OrderObject> orders) {
            Log.d(TAG, "this DealAdapter");
            this.orders = orders;
        }

        void setOrders(List<OrderObject> orders) {

            Log.d(TAG, "this setDeals");
            this.orders = orders;
            notifyDataSetChanged();
        }

        @Override
        public OrderViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            Log.d(TAG, "this onCreateViewHolder");
            return new OrderViewHolder(viewGroup);
        }

        @Override
        public void onBindViewHolder(OrderViewHolder viewHolder, int i) {
            Log.d(TAG, "this onBindViewHolder");
            viewHolder.bind(orders.get(i));
        }

        @Override
        public int getItemCount() {
            return orders.size();
        }
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        Log.d(TAG, "this onViewCreated");
        super.onViewCreated(view, savedInstanceState);
        //updateView();
    }

    @Override
    public void onPause() {

        Log.d(TAG, "this onPause");
        super.onPause();
    }

    @Override
    public void onStop() {
        super.onStop();

    }

    @Override
    public void onStart() {
        super.onStart();
        Log.d(TAG, "this on start");
        final RecyclerView recyclerView = (RecyclerView) getActivity().findViewById(R.id.order_list);
        recyclerView.setHasFixedSize(true);

        Log.d(TAG, "updating view");
        updateView();

    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        Log.d(TAG, "this onCreate");
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onResume() {
        Log.d(TAG, "this onResume");
        super.onResume();
        updateView();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        Log.d(TAG, "this onCreateView");
        orderRef = FirebaseDatabase.getInstance().getReference().child("orders");

        orderRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.d(TAG, "this onDataChange");
                for (DataSnapshot dealSnapshot : dataSnapshot.getChildren()) {
                    final OrderObject orderObject = new OrderObject();
                    Order o = new Order();
                    HashMap v= (HashMap)dealSnapshot.getValue();
                    Object[] orderString = v.values().toArray();
                    o.deal = orderString[0].toString();
                    o.quantity = orderString[1].toString();
                    o.shipped = orderString[2].toString();
                    o.user = orderString[3].toString();
                    o.paid = orderString[4].toString();


                    orderObject.paid = o.paid;
                    orderObject.quantity = o.quantity;
                    orderObject.shipped = o.shipped;

                    userRef =FirebaseDatabase.getInstance().getReference().child("users").child(o.user);

                    userRef.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            User orderUser = dataSnapshot.getValue(User.class);
                            orderObject.username = orderUser.username;
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });


                    dealRef =FirebaseDatabase.getInstance().getReference().child("deals").child(o.deal);
                    dealRef.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            Deal orderDeal = dataSnapshot.getValue(Deal.class);
                            orderObject.unitprice = orderDeal.price;
                            orderObject.dealtitle = orderDeal.title;
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                    orders.add(orderObject);
                }

                //userRef = Fi

                updateView();
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        return inflater.inflate(R.layout.fragment_order, container, false);
    }

    private void updateView() {
        Log.d(TAG, "this is updateView");
        RecyclerView recyclerView = (RecyclerView) getActivity().findViewById(R.id.order_list);
        recyclerView.setLayoutManager(
                new LinearLayoutManager(getActivity()));
        adapter = new OrderAdapter(orders);
        recyclerView.setAdapter(adapter);
    }

}
