package com.example.kudowazdroj.ui.accommodation;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.view.View;
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
    CardView cardView;

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
        cardView = findViewById(R.id.cardAccActivity);

        CardView cardViewBack = findViewById(R.id.cardAccGoBack);
        cardViewBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

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

        info3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String[] TO = {"lukipot1998@gmail.com"};
                String[] CC = {"xyz@gmail.com"};
                Intent emailIntent = new Intent(Intent.ACTION_SEND);
                emailIntent.setData(Uri.parse("mailto:"));
                emailIntent.setType("text/plain");

                emailIntent.putExtra(Intent.EXTRA_EMAIL, TO);

                try {
                    startActivity(Intent.createChooser(emailIntent, "Send mail..."));
                    finish();
                } catch (android.content.ActivityNotFoundException ex) {
                }
            }
        });

        info4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri number = Uri.parse("tel:" + info4.getText());
                Intent callIntent = new Intent(Intent.ACTION_DIAL, number);
                startActivity(callIntent);
            }
        });

        Bundle extras = getIntent().getExtras();
        String key = extras.getString(ARG_ACCOMMODATION_KEY);

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("Accommodation").child(key);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                title.setText(snapshot.child("name").getValue().toString());
                info0.setText(snapshot.child("rating").getValue().toString() + " / 5");
                info1.setText(snapshot.child("address").getValue().toString());
                info2.setText(snapshot.child("website").getValue().toString());
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

        //TextView t2 = (TextView) findViewById(R.id.text2);



    }
}