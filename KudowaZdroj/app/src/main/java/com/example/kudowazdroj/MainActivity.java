package com.example.kudowazdroj;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.Toast;

import com.example.kudowazdroj.ui.appInfo.AppInfoFragment;
import com.example.kudowazdroj.ui.accommodation.AccommodationFragment;
import com.example.kudowazdroj.ui.ad.AdFragment;
import com.example.kudowazdroj.ui.attractions.AttractionsFragment;
import com.example.kudowazdroj.ui.main.AboutKudowaFragment;
import com.example.kudowazdroj.ui.map.MapFragment;
import com.example.kudowazdroj.ui.news.NewsFragment;
import com.example.kudowazdroj.ui.restaurants.RestaurantsFragment;
import com.example.kudowazdroj.ui.trips.TripsFragment;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private boolean doubleBackToExitPressedOnce = false;

    private DrawerLayout drawerLayout;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mAuth = FirebaseAuth.getInstance();

        drawerLayout = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        if(savedInstanceState == null){
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new AboutKudowaFragment()).commit();
            navigationView.setCheckedItem(R.id.nav_message);
        }

        String email = "kudowa.app.user@gmail.com";
        String password = "cV=pPZg}#D)?q5WM";

        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseUser user = mAuth.getCurrentUser();
                        }
                    }
                });

   }

    @Override
    public void onResume() {
        super.onResume();
        FirebaseUser currentUser = mAuth.getCurrentUser();
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.nav_message:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new AboutKudowaFragment()).commit();
                break;
            case R.id.nav_message2:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new NewsFragment()).commit();
                break;
            case R.id.nav_message3:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new AttractionsFragment()).commit();
                break;
            case R.id.nav_message4:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new MapFragment()).commit();
                break;
            case R.id.nav_message5:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new TripsFragment()).commit();
                break;
            case R.id.nav_message6:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new AdFragment()).commit();
                break;
            case R.id.nav_message7:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new RestaurantsFragment()).commit();
                break;
            case R.id.nav_message8:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new AccommodationFragment()).commit();
                break;
            case R.id.nav_message9:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new AppInfoFragment()).commit();
                break;
        }

        drawerLayout.closeDrawer(GravityCompat.START);

        return true;
    }

    @Override
    public void onBackPressed() {
        if(drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }
        else {
            if (doubleBackToExitPressedOnce) {
                moveTaskToBack(true);
                android.os.Process.killProcess(android.os.Process.myPid());
                System.exit(1);
            }
            this.doubleBackToExitPressedOnce = true;
            Toast.makeText(this, "Naciśnij ponownie, aby wyjść", Toast.LENGTH_SHORT).show();

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    doubleBackToExitPressedOnce = false;
                }
            }, 2000);
        }
    }
}