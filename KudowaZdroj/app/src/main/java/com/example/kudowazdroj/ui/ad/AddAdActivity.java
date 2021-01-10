package com.example.kudowazdroj.ui.ad;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.kudowazdroj.MainActivity;
import com.example.kudowazdroj.R;
import com.example.kudowazdroj.database.Ad;
import com.example.kudowazdroj.database.Trip;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.UUID;

public class AddAdActivity extends AppCompatActivity {

    EditText edit1, edit2, edit3, edit4;
    ImageView addButton;
    ArrayList<Ad> myAds;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_ad);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

        edit1 = findViewById(R.id.add_ad_edit_1);
        edit2 = findViewById(R.id.add_ad_edit_2);
        edit3 = findViewById(R.id.add_ad_edit_3);
        edit4 = findViewById(R.id.add_ad_edit_4);
        addButton = findViewById(R.id.imageAddAd);

        CardView cardView = findViewById(R.id.cardAdGoBack);
        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        final DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Announcement");

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(checkConnection()) {
                    String id = UUID.randomUUID().toString();
                    String title = edit1.getText().toString();
                    String content = edit2.getText().toString();
                    String author = edit3.getText().toString();
                    String contact = edit4.getText().toString();
                    String date = Calendar.getInstance().getTime().toString();
                    if(isInputValid(title, content, author, contact)){
                        Ad ad = new Ad(id, title, date, content, author, contact);
                        reference.child(id).setValue(ad);
                        loadData();
                        myAds.add(ad);
                        saveData();
                        finish();
                    }
                    else Toast.makeText(getApplicationContext(), "Uzupełnij wszystkie pola", Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(getApplicationContext(), "Brak dostępu do Internetu", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private boolean isInputValid(String title, String content, String author, String contact){
        if(title.equals("") || content.equals("") || author.equals("") || contact.equals("")) return false;
        return true;
    }

    private boolean checkConnection(){
        ConnectivityManager cm = (ConnectivityManager)getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return (activeNetwork != null && activeNetwork.isConnectedOrConnecting());
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