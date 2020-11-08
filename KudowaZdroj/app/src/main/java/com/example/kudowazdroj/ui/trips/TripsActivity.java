package com.example.kudowazdroj.ui.trips;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

import com.example.kudowazdroj.MainActivity;
import com.example.kudowazdroj.R;
import com.example.kudowazdroj.database.Trip;
import com.example.kudowazdroj.ui.adapters.TripRouteAdapter;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class TripsActivity extends AppCompatActivity {

    public static final String ARG_TRIP_ID = "id";

    private ArrayList<Trip> trips;
    private Trip trip;

    Button mapButton, deleteButton;

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trips);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

        mapButton = findViewById(R.id.button_map);
        deleteButton = findViewById(R.id.button_delete);
        recyclerView = findViewById(R.id.recyclerView);

        trips = new ArrayList<>();
        loadData();

        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        adapter = new TripRouteAdapter(this, trip.getAttractions());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

        mapButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri uri = Uri.parse(trip.getMapLink());
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                intent.setPackage("com.google.android.apps.maps");
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteTrip();
            }
        });

    }

    private void deleteTrip(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setMessage("Czy na pewno chcesz usunąć wycieczkę?")
                .setCancelable(false)
                .setPositiveButton("Tak", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        trips.remove(trip);
                        saveData();
                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        intent.putExtra(MainActivity.ARG_FRAGMENT_ID, 4);
                        startActivity(intent);
                    }
                })
                .setNegativeButton("Nie", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    private void saveData() {
        SharedPreferences sharedPreferences = getSharedPreferences("shared preferences", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(trips);
        editor.putString("Trips", json);
        editor.apply();
    }

    private void loadData() {
        SharedPreferences sharedPreferences = getSharedPreferences("shared preferences", MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString("Trips", null);
        Type type = new TypeToken<ArrayList<Trip>>() {}.getType();
        trips = gson.fromJson(json, type);
        if (trips == null) {
            trips = new ArrayList<>();
        }
        Bundle extras = getIntent().getExtras();
        String id = extras.getString(ARG_TRIP_ID);
        for (Trip t : trips) if(t.getId().equals(id)) trip = t;
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        intent.putExtra(MainActivity.ARG_FRAGMENT_ID, 4);
        startActivity(intent);
        super.onBackPressed();
    }
}