package com.example.kudowazdroj.ui.attractions;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.kudowazdroj.LoadingDialog;
import com.example.kudowazdroj.R;
import com.example.kudowazdroj.ui.news.NewsActivity;

import java.util.ArrayList;

public class AttractionsActivity extends AppCompatActivity {

    public static final String ARG_ATTRACTION_ID = "id";
    TextView title;
    TextView content;
    ArrayList<ImageView> imageViews;
    final LoadingDialog loadingDialog = new LoadingDialog(AttractionsActivity.this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attractions);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);


    }
}