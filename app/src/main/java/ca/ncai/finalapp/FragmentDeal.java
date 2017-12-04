package ca.ncai.finalapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import android.util.Log;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.toolbox.NetworkImageView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import ca.ncai.finalapp.models.Deal;
import ca.ncai.finalapp.viewholder.DealViewHolder;

/**
 * Created by niang on 12/3/2017.
 */

public class FragmentDeal extends Fragment {
    private static final String TAG = MainActivity.class.getSimpleName();
    FirebaseDatabase database;
    DatabaseReference dealRef;
    DealAdapter adapter;
    private RecyclerView mRecycler;
    private LinearLayoutManager mManager;
    private FirebaseRecyclerAdapter<Deal, DealViewHolder> mAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        database = FirebaseDatabase.getInstance();
        dealRef = database.getReference();
        View rootView = inflater.inflate(R.layout.fragment_deal, container, false);

        mRecycler = rootView.findViewById(R.id.deal_list);
        mRecycler.setHasFixedSize(true);

        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mManager = new LinearLayoutManager(getActivity());
        mManager.setReverseLayout(true);
        mManager.setStackFromEnd(true);
        mRecycler.setLayoutManager(mManager);

        Query postsQuery = getQuery(dealRef);

        FirebaseRecyclerOptions options = new FirebaseRecyclerOptions.Builder<Deal>()
                .setQuery(postsQuery, Deal.class)
                .build();
        mAdapter = new FirebaseRecyclerAdapter<Deal, DealViewHolder>(options){

            @Override
            public DealViewHolder onCreateViewHolder(View viewGroup, int viewType) {
                LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
                return new DealViewHolder(inflater.inflate(R.layout.deal_entry, viewGroup, false));
            }

            @Override
            protected void onBindViewHolder(DealViewHolder holder, int position, Deal model) {
                final DatabaseReference dealRef = getRef(position);
                final String dealKey = dealRef.getKey();
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // Launch PostDetailActivity
                        Intent intent = new Intent(getActivity(), DealDetailActivity.class);
                        intent.putExtra(DealDetailActivity.EXTRA_DEAL_KEY, dealKey);
                        startActivity(intent);
                    }
                });


            }
        };
        mRecycler.setAdapter(mAdapter);
    }

    @Override
    public void onStop() {
        super.onStop();
        if(mAdapter != null){
            mAdapter.stopListening();
        }
    }

    public String getUid() {
        return FirebaseAuth.getInstance().getCurrentUser().getUid();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
//        Toolbar appBar = getActivity().findViewById(R.id.app_bar);
//        getActivity().setActionBar(appBar);
        ImageRequester imageRequester = ImageRequester.getInstance(getActivity());
        Deal headerDeal = getHeaderDeal(dealsArrayList);
        NetworkImageView headerImage = (NetworkImageView) getActivity().findViewById(R.id.app_bar_image);
        imageRequester.setImageFromUrl(headerImage, headerDeal.url);

        final RecyclerView recyclerView = (RecyclerView) getActivity().findViewById(R.id.deal_list);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2)); //SharePreferences
        adapter = new DealAdapter(dealsArrayList, imageRequester);
        recyclerView.setAdapter(adapter);

        GridLayoutManager layoutManager =
                (GridLayoutManager) recyclerView.getLayoutManager();
        layoutManager.scrollToPositionWithOffset(0, 0);
        getDeals();

    }

    public Query getQuery(DatabaseReference databaseReference)
    {

    }

    @Override
    public void onStart() {
        super.onStart();

        if(mAdapter != null){
            mAdapter.startListening();
        }
    }


    private Deal getHeaderDeal(ArrayList<Deal> dealsArrayList) {
        if(dealsArrayList.size() ==0)
        {
            throw new IllegalArgumentException("No deals aviliable exception.");
        }
        return dealsArrayList.get((int)(Math.random() * dealsArrayList.size()));
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    private static final class DealAdapter extends RecyclerView.Adapter<DealViewHolder> {
        private List<Deal> deals;
        private final ImageRequester imageRequester;

        DealAdapter(List<Deal> deals, ImageRequester imageRequester) {
            this.deals = deals;
            this.imageRequester = imageRequester;
        }

        void setDeals(List<Deal> deals) {
            this.deals = deals;
            notifyDataSetChanged();
        }

        @Override
        public DealViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            return new DealViewHolder(viewGroup);
        }

        @Override
        public void onBindViewHolder(DealViewHolder viewHolder, int i) {
            viewHolder.bind(deals.get(i), imageRequester);
        }

        @Override
        public int getItemCount() {
            return deals.size();
        }
    }
}
