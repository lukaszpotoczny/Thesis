package com.example.kudowazdroj.ui.attractions;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.content.res.ResourcesCompat;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.kudowazdroj.MainActivity;
import com.example.kudowazdroj.R;
import com.example.kudowazdroj.database.Attraction;
import com.example.kudowazdroj.ui.restaurants.RestaurantsActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.squareup.picasso.Picasso;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class AttractionsActivity extends AppCompatActivity {

    public static final String ARG_ATTRACTION_KEY = "key";

    TextView title, content, info0, info1, info2;
    ImageView titleImage, heartImage;
    ImageView[] imageViews;
    ProgressBar progressBar;

    ArrayList<String> images = new ArrayList<>();
    ArrayList<Attraction> favourites;
    Attraction attraction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attractions);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

        title = findViewById(R.id.attr_name);
        content = findViewById(R.id.attr_text_1);
        titleImage = findViewById(R.id.attr_image_1);
        heartImage = findViewById(R.id.imageHeart);
        info0 = findViewById(R.id.attr_info_0);
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

        info0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(info0.getText().equals("Ulubione")) removeFromFavourite();
                else addToFavourite();
            }
        });

        heartImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(info0.getText().equals("Ulubione")) removeFromFavourite();
                else addToFavourite();
            }
        });

        info1.setPaintFlags(info1.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        info1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String search = title.getText().toString() + "+Kudowa+Zdrój";
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
                attraction = new Attraction(Integer.parseInt(snapshot.child("id").getValue().toString()), snapshot.child("name").getValue().toString(),
                        snapshot.child("content").getValue().toString(), Integer.parseInt(snapshot.child("time").getValue().toString()), snapshot.child("photo").getValue().toString());

                title.setText(attraction.getName());
                content.setText(attraction.getContent());
                info2.setText(attraction.getTime() + " min");

                String url = attraction.getPhoto();
                if(!url.equals("")) Picasso.with(AttractionsActivity.this).load(url).into(titleImage);

                for(DataSnapshot dataSnapshot : snapshot.child("images").getChildren()){
                    images.add(dataSnapshot.getValue().toString());
                }

                for(int i=0; i<imageViews.length && i<images.size(); i++){
                    Picasso.with(getApplicationContext()).load(images.get(i)).into(imageViews[i]);
                    imageViews[i].setVisibility(View.VISIBLE);
                }
                loadData();
                progressBar.setVisibility(View.INVISIBLE);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                progressBar.setVisibility(View.INVISIBLE);
            }
        });

    }

    private void addToFavourite(){
        info0.setText("Ulubione");
        info0.setTextSize(17);
        Typeface typeface = ResourcesCompat.getFont(getApplicationContext(), R.font.playfair_display);
        info0.setTypeface(typeface);
        heartImage.setImageResource(R.drawable.heart);
        favourites.add(attraction);
        saveData();
    }

    private void removeFromFavourite(){
        info0.setText("Dodaj do ulubionych");
        info0.setTextSize(16);
        info0.setTypeface(Typeface.DEFAULT);
        Typeface typeface = ResourcesCompat.getFont(getApplicationContext(), R.font.playfair_display);
        info0.setTypeface(typeface);
        heartImage.setImageResource(R.drawable.heart_border);
        for(int i=0; i<favourites.size(); i++) if(favourites.get(i).getId() == attraction.getId()) favourites.remove(i);
        saveData();
    }

    private void saveData() {
        SharedPreferences sharedPreferences = getSharedPreferences("shared preferences", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(favourites);
        editor.putString("task list", json);
        editor.apply();
    }

    private void loadData() {
        SharedPreferences sharedPreferences = getSharedPreferences("shared preferences", MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString("task list", null);
        Type type = new TypeToken<ArrayList<Attraction>>() {}.getType();
        favourites = gson.fromJson(json, type);
        if (favourites == null) {
            favourites = new ArrayList<>();
        }
        for(int i=0; i<favourites.size(); i++) {
            if(favourites.get(i).getId() == attraction.getId()) {
                info0.setText("Ulubione");
                info0.setTextSize(17);
                Typeface typeface = ResourcesCompat.getFont(getApplicationContext(), R.font.playfair_display);
                info0.setTypeface(typeface);
                heartImage.setImageResource(R.drawable.heart);
            }
        }
    }
}