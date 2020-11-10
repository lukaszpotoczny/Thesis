package com.example.kudowazdroj.ui.splash;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Pair;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.kudowazdroj.MainActivity;
import com.example.kudowazdroj.R;

public class SplashActivity extends AppCompatActivity {

    private static int SPLASH_SCREEN = 3000;

    private Animation topAnim, bottomAnim;
    private ImageView logo, text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

        topAnim = AnimationUtils.loadAnimation(this, R.anim.top_anim);
        bottomAnim = AnimationUtils.loadAnimation(this, R.anim.bottom_anim);

        logo = findViewById(R.id.splash_logo);
        text = findViewById(R.id.splash_text);

        logo.setAnimation(topAnim);
        text.setAnimation(bottomAnim);

        new Handler().postDelayed(new Runnable(){
            @Override
            public void run() {
                Intent intent = new Intent(SplashActivity.this, MainActivity.class);

                Pair pair = new Pair<View,String>(logo, "logo");

                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(SplashActivity.this, pair);
                startActivity(intent, options.toBundle());
            }
        }, SPLASH_SCREEN);
    }
}