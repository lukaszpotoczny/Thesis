package com.example.kudowazdroj.ui.attractions;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.graphics.Paint;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.kudowazdroj.R;
import com.example.kudowazdroj.database.Attraction;
import com.example.kudowazdroj.ui.restaurants.RestaurantsActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;

public class AttractionsActivity extends AppCompatActivity {

    public static final String ARG_ATTRACTION_KEY = "key";
    TextView title, content, info1, info2;
    ImageView titleImage;
    ImageView[] imageViews;
    ProgressBar progressBar;

    ArrayList<String> images = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attractions);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

        title = findViewById(R.id.attr_name);
        content = findViewById(R.id.attr_text_1);
        titleImage = findViewById(R.id.attr_image_1);
        info1 = findViewById(R.id.attr_info_1);
        info2 = findViewById(R.id.attr_info_2);

        imageViews = new ImageView[6];
        imageViews[0] = findViewById(R.id.attr_image_2);
        imageViews[1] = findViewById(R.id.attr_image_3);
        imageViews[2] = findViewById(R.id.attr_image_4);
        imageViews[3] = findViewById(R.id.attr_image_5);
        imageViews[4] = findViewById(R.id.attr_image_6);
        imageViews[5] = findViewById(R.id.attr_image_7);

        progressBar = findViewById(R.id.newsProgressBar2);

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

        CardView cardView = findViewById(R.id.cardAttractionsGoBack);
        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        Bundle extras = getIntent().getExtras();
        String key = extras.getString(ARG_ATTRACTION_KEY);

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("Attractions").child(key);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                title.setText(snapshot.child("name").getValue().toString());
                content.setText(snapshot.child("content").getValue().toString());
                info2.setText(snapshot.child("time").getValue().toString() + " min");

                String url = snapshot.child("photo").getValue().toString();
                if(!url.equals("")) Picasso.with(AttractionsActivity.this).load(url).into(titleImage);

                for(DataSnapshot dataSnapshot : snapshot.child("images").getChildren()){
                    images.add(dataSnapshot.getValue().toString());
                }

                for(int i=0; i<imageViews.length && i<images.size(); i++){
                    Picasso.with(getApplicationContext()).load(images.get(i)).into(imageViews[i]);
                    imageViews[i].setVisibility(View.VISIBLE);
                }
                progressBar.setVisibility(View.INVISIBLE);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                progressBar.setVisibility(View.INVISIBLE);
            }
        });

    }
}