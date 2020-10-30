package com.example.kudowazdroj.ui.ad;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.kudowazdroj.R;
import com.example.kudowazdroj.ui.main.AboutKudowaFragment;
import com.example.kudowazdroj.ui.restaurants.RestaurantsActivity;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class AdActivity extends AppCompatActivity {

    public static final String ARG_AD_ID = "id";

    TextView title, date, content, author, contact;
    ImageView pin;


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

        Bundle extras = getIntent().getExtras();
        String key = extras.getString(ARG_AD_ID);

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("Announcement").child(key);
        databaseReference.addValueEventListener(new ValueEventListener() {
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
        });
    }
}