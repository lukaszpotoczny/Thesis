package com.example.kudowazdroj.ui.ad;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.WindowManager;

import com.example.kudowazdroj.R;

public class AdActivity extends AppCompatActivity {

    public static final String ARG_AD_ID = "id";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ad);

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }
}