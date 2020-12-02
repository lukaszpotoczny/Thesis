package com.example.kudowazdroj.ui.ad;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kudowazdroj.MainActivity;
import com.example.kudowazdroj.R;
import com.example.kudowazdroj.database.Ad;
import com.example.kudowazdroj.database.Trip;
import com.example.kudowazdroj.ui.main.AboutKudowaFragment;
import com.example.kudowazdroj.ui.restaurants.RestaurantsActivity;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.squareup.picasso.Picasso;

import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.Locale;

public class AdActivity extends AppCompatActivity {

    public static final String ARG_AD_ID = "id";

    TextView title, date, content, author, contact;
    ImageView pin, buttonDelete;

    ArrayList<Ad> myAds;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ad);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

        pin = findViewById(R.id.ad_image_pin);
        title = findViewById(R.id.ad_title);
        date = findViewById(R.id.ad_date);
        content = findViewById(R.id.ad_text_1);
        author = findViewById(R.id.ad_text_2);
        contact = findViewById(R.id.ad_text_3);
        buttonDelete = findViewById(R.id.imageDelete);

        CardView cardView = findViewById(R.id.cardAdGoBack);
        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        Bundle extras = getIntent().getExtras();
        final String key = extras.getString(ARG_AD_ID);

        loadData();

        for(int i=0; i<myAds.size(); i++) if(myAds.get(i).getId().equals(key)) buttonDelete.setVisibility(View.VISIBLE);

        contact.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText("contactInfo", contact.getText().toString());
                clipboard.setPrimaryClip(clip);
                Toast.makeText(getApplicationContext(), "Skopiowano do schowka", Toast.LENGTH_SHORT).show();
                return false;
            }
        });

        final ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                title.setText(snapshot.child("title").getValue().toString());
                content.setText(snapshot.child("content").getValue().toString());
                author.setText(snapshot.child("author").getValue().toString());
                contact.setText(snapshot.child("contact").getValue().toString());

                String stringDate = snapshot.child("date").getValue().toString();
                SimpleDateFormat format = new SimpleDateFormat("E MMM d HH:mm:ss z yyyy", Locale.getDefault());
                try {
                    Date d = format.parse(stringDate);
                    SimpleDateFormat df = new SimpleDateFormat("dd.MM.yyyy", Locale.getDefault());
                    String formattedDate = df.format(d);
                    date.setText(formattedDate);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        };

        final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("Announcement").child(key);
        databaseReference.addValueEventListener(valueEventListener);

        buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteAd(databaseReference, valueEventListener, key);
            }
        });


    }

    private void deleteAd(final DatabaseReference databaseReference, final ValueEventListener valueEventListener, final String key){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Czy na pewno chcesz usunąć ogłoszenie?")
                .setCancelable(false)
                .setPositiveButton("Tak", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Iterator<Ad> iterator = myAds.iterator();
                        while (iterator.hasNext()) {
                            Ad ad = iterator.next();
                            if(ad.getId().equals(key)) iterator.remove();
                        }
                        databaseReference.removeEventListener(valueEventListener);
                        saveData();
                        finish();
                        final Handler handler = new Handler(Looper.getMainLooper());
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                databaseReference.removeValue();
                            }
                        }, 200);
                    }
                })
                .setNegativeButton("Nie", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    private void saveData() {
        SharedPreferences sharedPreferences = getSharedPreferences("shared preferences", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(myAds);
        editor.putString("myAds", json);
        editor.apply();
    }

    private void loadData() {
        SharedPreferences sharedPreferences = getSharedPreferences("shared preferences", MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString("myAds", null);
        Type type = new TypeToken<ArrayList<Ad>>() {}.getType();
        myAds = gson.fromJson(json, type);
        if (myAds == null) {
            myAds = new ArrayList<>();
        }
    }

}