package com.example.kudowazdroj.ui.attractions;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.kudowazdroj.R;

import java.util.ArrayList;

public class AttractionsActivity extends AppCompatActivity {

    public static final String ARG_ATTRACTION_ID = "id";
    TextView title;
    TextView content;
    ArrayList<ImageView> imageViews;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attractions);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

        CardView cardView = findViewById(R.id.cardAttractionsGoBack);
        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }
}