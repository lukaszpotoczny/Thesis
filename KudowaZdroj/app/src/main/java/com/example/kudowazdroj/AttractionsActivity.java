package com.example.kudowazdroj;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class AttractionsActivity extends AppCompatActivity {


    public static final String ARG_ATTRACTION_ID = "id";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.attractions_activity);
    }
}