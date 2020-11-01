package com.example.kudowazdroj.ui.restaurants;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.kudowazdroj.R;
import com.example.kudowazdroj.database.Restaurant;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.util.Collections;
import java.util.Map;

public class RestaurantsActivity extends AppCompatActivity {

    public static final String ARG_RESTAURANT_KEY = "key";

    TextView title, info1, info2, info3, info4, content;
    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurants);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

        title = findViewById(R.id.restaurant_name);
        info1 = findViewById(R.id.restaurant_info_1);
        info2 = findViewById(R.id.restaurant_info_2);
        info3 = findViewById(R.id.restaurant_info_3);
        info4 = findViewById(R.id.restaurant_info_4);
        content = findViewById(R.id.restaurant_text_1);
        imageView = findViewById(R.id.restaurant_image_1);

        Bundle extras = getIntent().getExtras();
        String key = extras.getString(ARG_RESTAURANT_KEY);

        info1.setPaintFlags(info1.getPaintFlags() |   Paint.UNDERLINE_TEXT_FLAG);
        info1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String search = title.getText().toString();
                search = search.replaceAll(" ", "+");
                Uri uri = Uri.parse("https://www.google.com/maps/search/" + search);

                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                intent.setPackage("com.google.android.apps.maps");
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });

        info2.setMovementMethod(LinkMovementMethod.getInstance());

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("Restaurants").child(key);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                title.setText(snapshot.child("name").getValue().toString());
                info1.setText(snapshot.child("address").getValue().toString());
                info2.setText(snapshot.child("rating").getValue().toString() + " / 5");
                info3.setText(snapshot.child("website").getValue().toString());
                info4.setText(snapshot.child("phone").getValue().toString());
                content.setText(snapshot.child("content").getValue().toString());
                String url = snapshot.child("image").getValue().toString();
                if(!url.equals("")) Picasso.with(RestaurantsActivity.this).load(url).into(imageView);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });


    }
}