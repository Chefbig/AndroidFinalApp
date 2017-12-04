package ca.ncai.finalapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class DealDetailActivity extends AppCompatActivity {
    public static final String EXTRA_DEAL_KEY = "deal_key";
    private String mDealKey;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deal_detail);

        mDealKey = getIntent().getStringExtra(EXTRA_DEAL_KEY);
        if (mDealKey == null) {
            throw new IllegalArgumentException("Must pass EXTRA_DEAL_KEY");
        }
    }
}
