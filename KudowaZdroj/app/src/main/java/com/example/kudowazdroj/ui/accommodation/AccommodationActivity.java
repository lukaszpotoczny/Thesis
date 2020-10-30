package com.example.kudowazdroj.ui.accommodation;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.kudowazdroj.R;
import com.example.kudowazdroj.ui.restaurants.RestaurantsActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class AccommodationActivity extends AppCompatActivity {

    public static final String ARG_ACCOMMODATION_KEY = "key";

    TextView title, info0, info1, info2, info3, info4, content;
    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accommodation);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

        title = findViewById(R.id.accommodation_name);
        info0 = findViewById(R.id.accommodation_info_0);
        info1 = findViewById(R.id.accommodation_info_1);
        info2 = findViewById(R.id.accommodation_info_2);
        info3 = findViewById(R.id.accommodation_info_3);
        info4 = findViewById(R.id.accommodation_info_4);
        content = findViewById(R.id.accommodation_text_1);
        imageView = findViewById(R.id.accommodation_image_1);

        Bundle extras = getIntent().getExtras();
        String key = extras.getString(ARG_ACCOMMODATION_KEY);

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("Accommodation").child(key);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                title.setText(snapshot.child("name").getValue().toString());
                info0.setText(snapshot.child("rating").getValue().toString());
                info1.setText(snapshot.child("address").getValue().toString());
                info2.setText(snapshot.child("website").getValue().toString() + " / 5");
                info3.setText(snapshot.child("email").getValue().toString());
                info4.setText(snapshot.child("phone").getValue().toString());
                content.setText(snapshot.child("content").getValue().toString());
                String url = snapshot.child("image").getValue().toString();
                if(!url.equals("")) Picasso.with(AccommodationActivity.this).load(url).into(imageView);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }
}