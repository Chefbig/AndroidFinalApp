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

import ca.ncai.finalapp.models.Product;

public class ProductActivity extends AppCompatActivity {

    DatabaseReference myDatabaseRef;
    final ArrayList<Product> myProductEntryList= new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);

        myDatabaseRef = FirebaseDatabase.getInstance().getReference().child("products");
    }

    @Override
    protected void onStart() {
        super.onStart();

        ValueEventListener productListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot productSnapshot: dataSnapshot.getChildren()) {
                    Product p = productSnapshot.getValue(Product.class);
//                    String price = "", description = "", title = "", url = "";
//                    price = productSnapshot.child("price").getValue(String.class);
//                    description = productSnapshot.child("description").getValue(String.class);
//                    title = productSnapshot.child("title").getValue(String.class);
//                    url = productSnapshot.child("url").getValue(String.class);
//                    Product p = new Product(title, url, price, description);
                    Toast.makeText(ProductActivity.this, p.title, Toast.LENGTH_LONG).show();
                    myProductEntryList.add(p);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };

        myDatabaseRef.addValueEventListener(productListener);
    }
}
