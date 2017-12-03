package ca.ncai.finalapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

public class ProductActivity extends AppCompatActivity {

    DatabaseReference myDatabaseRef;
    final ArrayList<ProductEntry> myProductEntryList= new ArrayList<>();
    public class ProductEntry {
        public String price;
        public String description;
        public String title;
        public String url;

        public ProductEntry(){}
        public ProductEntry(String title, String url, String price, String description) {
            this.title = title;
            this.url = url;
            this.price = price;
            this.description = description;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);

        myDatabaseRef = FirebaseDatabase.getInstance().getReference().child("products").child("0");
    }

    @Override
    protected void onStart() {
        super.onStart();

        ValueEventListener productListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                    String price="", description="", title="", url="";
                    price = dataSnapshot.child("price").getValue(String.class);
                    description = dataSnapshot.child("description").getValue(String.class);
                    title = dataSnapshot.child("title").getValue(String.class);
                    url = dataSnapshot.child("url").getValue(String.class);
                    ProductEntry p = new ProductEntry(title, url, price, description);
                    Toast.makeText(ProductActivity.this, p.title, Toast.LENGTH_LONG).show();

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };

        myDatabaseRef.addValueEventListener(productListener);
    }
}
