package com.example.kudowazdroj.ui.ad;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;

import com.example.kudowazdroj.R;
import com.example.kudowazdroj.ui.main.AboutKudowaFragment;
import com.google.android.material.navigation.NavigationView;

public class AdActivity extends AppCompatActivity {

    public static final String ARG_AD_ID = "id";

    ImageView pin;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ad);

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

        pin = findViewById(R.id.ad_image_pin);
        //pin.bringToFront();
    }
}