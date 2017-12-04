package ca.ncai.finalapp;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.toolbox.NetworkImageView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import ca.ncai.finalapp.models.Deal;
import ca.ncai.finalapp.viewholder.DealViewHolder;

/**
 * Created by niang on 12/3/2017.
 */

public class FragmentDeal extends Fragment {
    ArrayList<Deal> deals= new ArrayList<>();
    DatabaseReference myDatabaseRef;
    private DealAdapter adapter;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        Log.d("Nico", "this onCreateView");
        //myDatabaseRef = FirebaseDatabase.getInstance().getReference().child("deals");

        //myFirebase = new Firebase("https://database-f2992.firebaseio.com/products");
        myDatabaseRef = FirebaseDatabase.getInstance().getReference().child("deals");


        myDatabaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.d("Nico", "this onDataChange");
                for (DataSnapshot dealSnapshot : dataSnapshot.getChildren()) {
                    Deal d = dealSnapshot.getValue(Deal.class);
                    deals.add(d);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

//        ValueEventListener dealListener = new ValueEventListener() {
//
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//
//                Log.d("Nico", "this onDataChange");
//                for (DataSnapshot dealSnapshot : dataSnapshot.getChildren()) {
//                    Deal d = dealSnapshot.getValue(Deal.class);
//                    deals.add(d);
//                }
//            }
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//
//            }
//        };
//
//        myDatabaseRef.addValueEventListener(dealListener);

        return inflater.inflate(R.layout.fragment_deal, container, false);
    }

    private Deal getGoodDeal(ArrayList<Deal> deals) {
        Log.d("Nico", "this getGoodDeal");
        if(deals.size() !=0){
            return deals.get((int) Math.random()*deals.size());
        }
        else
        {
           throw new IllegalArgumentException("No deal exception");
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.d("Nico", "this on start");


       ImageRequester imageRequester = ImageRequester.getInstance(getActivity());

        final RecyclerView recyclerView = (RecyclerView) getActivity().findViewById(R.id.deal_list);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(
                new GridLayoutManager(getActivity(), getResources().getInteger(R.integer.column_count)));
        adapter = new DealAdapter(deals, imageRequester);
        recyclerView.setAdapter(adapter);
        Log.d("Nico", "checking deals");
//
//        Deal goodDeal = getGoodDeal(deals);
//        NetworkImageView headerImage = (NetworkImageView) getActivity().findViewById(R.id.app_bar_image);
//        imageRequester.setImageFromUrl(headerImage, goodDeal.url);

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {

        Log.d("Nico", "this onCreate");
        super.onCreate(savedInstanceState);
//        Toolbar appBar = (Toolbar) getActivity().findViewById(R.id.app_bar);
//        getContext().setSupportActionBar(appBar);

    }

    private static final class DealAdapter extends RecyclerView.Adapter<DealViewHolder> {
        private List<Deal> deals;
        private final ImageRequester imageRequester;

        DealAdapter(List<Deal> deals, ImageRequester imageRequester) {

            Log.d("Nico", "this DealAdapter");
            this.deals = deals;
            this.imageRequester = imageRequester;
        }

        void setDeals(List<Deal> deals) {

            Log.d("Nico", "this setDeals");
            this.deals = deals;
            notifyDataSetChanged();
        }

        @Override
        public DealViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {

            Log.d("Nico", "this onCreateViewHolder");
            return new DealViewHolder(viewGroup);
        }

        @Override
        public void onBindViewHolder(DealViewHolder viewHolder, int i) {

            Log.d("Nico", "this onBindViewHolder");
            viewHolder.bind(deals.get(i), imageRequester);
        }

        @Override
        public int getItemCount() {
            return deals.size();
        }
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {

        Log.d("Nico", "this onViewCreated");
        super.onViewCreated(view, savedInstanceState);
    }
}
