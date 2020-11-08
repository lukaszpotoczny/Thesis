package com.example.kudowazdroj.ui.ad;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

import com.example.kudowazdroj.MainActivity;
import com.example.kudowazdroj.R;
import com.example.kudowazdroj.database.Ad;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;
import java.util.UUID;

public class AddAdActivity extends AppCompatActivity {

    EditText edit1, edit2, edit3, edit4;
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_ad);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

        edit1 = findViewById(R.id.add_ad_edit_1);
        edit2 = findViewById(R.id.add_ad_edit_2);
        edit3 = findViewById(R.id.add_ad_edit_3);
        edit4 = findViewById(R.id.add_ad_edit_4);
        button = findViewById(R.id.add_ad_button);

        CardView cardView = findViewById(R.id.cardAdGoBack);
        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        final DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Announcement");

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String id = UUID.randomUUID().toString();
                String title = edit1.getText().toString();
                String content = edit2.getText().toString();
                String author = edit3.getText().toString();
                String contact = edit4.getText().toString();
                String date = Calendar.getInstance().getTime().toString();
                Ad ad = new Ad(id, title, date, content, author, contact);

                reference.child(id).setValue(ad);

                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                intent.putExtra(MainActivity.ARG_FRAGMENT_ID, 5);
                startActivity(intent);
            }
        });

    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        intent.putExtra(MainActivity.ARG_FRAGMENT_ID, 5);
        startActivity(intent);
        super.onBackPressed();
    }
}