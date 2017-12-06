package ca.ncai.finalapp;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
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
    private static final String TAG = "FragmentDeal";
    final ArrayList<Deal> deals= new ArrayList<>();
    DatabaseReference myDatabaseRef;
    private DealAdapter adapter;
    ImageRequester imageRequester;
    int number_of_deals_column;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        Log.d(TAG, "this onCreateView");
        myDatabaseRef = FirebaseDatabase.getInstance().getReference().child("deals");
        myDatabaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.d(TAG, "this onDataChange");
                for (DataSnapshot dealSnapshot : dataSnapshot.getChildren()) {
                    Deal d = dealSnapshot.getValue(Deal.class);
                    deals.add(d);
                }
                updateView();
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        return inflater.inflate(R.layout.fragment_deal, container, false);
    }

    private void updateView() {
        Log.d(TAG, "this is updateView");
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
        String spref = prefs.getString("number_of_colums","1");
        number_of_deals_column = Integer.valueOf(spref);
        RecyclerView recyclerView = (RecyclerView) getActivity().findViewById(R.id.deal_list);
        recyclerView.setLayoutManager(
                new GridLayoutManager(getActivity(), number_of_deals_column));
        adapter = new DealAdapter(deals, imageRequester);
        recyclerView.setAdapter(adapter);
        adapter = new DealAdapter(deals, imageRequester);
        recyclerView.setAdapter(adapter);
//        Deal goodDeal = getGoodDeal(deals);
//        NetworkImageView headerImage = (NetworkImageView) getActivity().findViewById(R.id.app_bar_image);
//        imageRequester.setImageFromUrl(headerImage, goodDeal.url);
    }

    private Deal getGoodDeal(ArrayList<Deal> deals) {
        Log.d(TAG, "this getGoodDeal");
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
        Log.d(TAG, "this on start");

       imageRequester = ImageRequester.getInstance(getActivity());
        final RecyclerView recyclerView = (RecyclerView) getActivity().findViewById(R.id.deal_list);
        recyclerView.setHasFixedSize(true);

        Log.d(TAG, "updating view");
        updateView();

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        Log.d(TAG, "this onCreate");
        super.onCreate(savedInstanceState);
    }

    private static final class DealAdapter extends RecyclerView.Adapter<DealViewHolder> {
        private List<Deal> deals;
        private final ImageRequester imageRequester;

        DealAdapter(List<Deal> deals, ImageRequester imageRequester) {
            Log.d(TAG, "this DealAdapter");
            this.deals = deals;
            this.imageRequester = imageRequester;
        }

        void setDeals(List<Deal> deals) {

            Log.d(TAG, "this setDeals");
            this.deals = deals;
            notifyDataSetChanged();
        }

        @Override
        public DealViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            Log.d(TAG, "this onCreateViewHolder");
            return new DealViewHolder(viewGroup);
        }

        @Override
        public void onBindViewHolder(DealViewHolder viewHolder, int i) {
            Log.d(TAG, "this onBindViewHolder");
            viewHolder.bind(deals.get(i), imageRequester);
        }

        @Override
        public int getItemCount() {
            return deals.size();
        }
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        Log.d(TAG, "this onViewCreated");
        super.onViewCreated(view, savedInstanceState);
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
    public void onResume() {
        Log.d(TAG, "this onResume");
        super.onResume();
        updateView();
    }
}
