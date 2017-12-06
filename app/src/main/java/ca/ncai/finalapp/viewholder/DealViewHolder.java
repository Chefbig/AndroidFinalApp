package ca.ncai.finalapp.viewholder;

import android.support.v7.widget.RecyclerView.ViewHolder;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.toolbox.NetworkImageView;

import ca.ncai.finalapp.ImageRequester;
import ca.ncai.finalapp.R;
import ca.ncai.finalapp.models.Deal;

/**
 * Created by niang on 12/3/2017.
 */

public class DealViewHolder extends ViewHolder {
    private static final String TAG = "DealViewHolder";
    private final NetworkImageView imageView;
    private final TextView priceView;

    public DealViewHolder(ViewGroup parent) {
        super(LayoutInflater.from(parent.getContext()).inflate(
                R.layout.deal_entry, parent, false));
        Log.d(TAG, "this DealViewHolder");
        imageView = (NetworkImageView) itemView.findViewById(R.id.image);
        priceView = (TextView) itemView.findViewById(R.id.price);
        itemView.setOnClickListener(clickListener);
    }
    private final View.OnClickListener clickListener =
            new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Deal deal = (Deal) v.getTag(R.id.tag_deal);
                    // TODO: show deal details
                }
            };

    public void bind(Deal deal, ImageRequester imageRequester) {
        itemView.setTag(R.id.tag_deal, deal);
        imageRequester.setImageFromUrl(imageView, deal.url);
        priceView.setText(deal.price);
    }
}
