package com.example.kudowazdroj.ui.trips;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.kudowazdroj.R;
import com.example.kudowazdroj.database.Attraction;
import com.example.kudowazdroj.database.Location;
import com.example.kudowazdroj.database.Trip;
import com.example.kudowazdroj.ui.adapters.AttractionsAdapter;
import com.example.kudowazdroj.ui.adapters.TripPickAdapter;
import com.example.kudowazdroj.ui.adapters.TripsAdapter;
import com.example.kudowazdroj.ui.attractions.AttractionsActivity;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;

public class TripsFragment extends Fragment {

    ArrayList<Trip> trips;
    TripsAdapter tripsAdapter;
    GridView gridView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.trips_fragment, container, false);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle(R.string.nav_menu_5);

        gridView = root.findViewById(R.id.gridTrips);
        trips = new ArrayList<>();

        loadData();

        tripsAdapter = new TripsAdapter(getActivity().getApplicationContext(), trips);
        gridView.setAdapter(tripsAdapter);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Trip trip = trips.get(i);
                Intent intent = new Intent(getActivity(), TripsActivity.class);
                intent.putExtra(TripsActivity.ARG_TRIP_ID, trip.getId());
                startActivity(intent);
            }
        });

        Button addButton = root.findViewById(R.id.ad_button);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), AddTripActivity.class);
                startActivity(intent);
            }
        });

        return root;
    }

    private void loadData() {
        SharedPreferences sharedPreferences = this.getActivity().getSharedPreferences("shared preferences", MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString("Trips", null);
        Type type = new TypeToken<ArrayList<Trip>>() {}.getType();
        trips = gson.fromJson(json, type);
        if (trips == null) {
            trips = new ArrayList<>();
        }
    }

}
