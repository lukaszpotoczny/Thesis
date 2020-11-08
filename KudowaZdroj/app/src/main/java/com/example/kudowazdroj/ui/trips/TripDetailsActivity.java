package com.example.kudowazdroj.ui.trips;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import com.example.kudowazdroj.MainActivity;
import com.example.kudowazdroj.R;
import com.example.kudowazdroj.database.Attraction;
import com.example.kudowazdroj.database.Location;
import com.example.kudowazdroj.database.Restaurant;
import com.example.kudowazdroj.database.Trip;
import com.example.kudowazdroj.ui.adapters.TripRouteAdapter;
import com.example.kudowazdroj.ui.attractions.AttractionsActivity;
import com.example.kudowazdroj.ui.map.MapFragment;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.apache.commons.io.IOUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

public class TripDetailsActivity extends AppCompatActivity implements DialogTrip.DialogTripListener {

    ArrayList<Attraction> selectedAttractions;
    ArrayList<Attraction> finalRoute;
    ArrayList<Location> chosenRoute;
    ArrayList<Trip> trips;

    private int totalTime = 0;
    private String search = "";

    Place[] places;
    double[][] distanceMatrix;

    Button mapButton, saveButton;

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trip_details);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

        mapButton = findViewById(R.id.button_map);
        saveButton = findViewById(R.id.button_save);
        recyclerView = findViewById(R.id.recyclerView);

        chosenRoute = new ArrayList<>();
        finalRoute = new ArrayList<>();

        Bundle bundle = getIntent().getExtras();
        selectedAttractions = (ArrayList<Attraction>) bundle.getSerializable("attractions");
        changeToLocations();

        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        adapter = new TripRouteAdapter(this, finalRoute);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

        mapButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                search = "";
                for(int i=0; i<chosenRoute.size(); i++){
                   String s = chosenRoute.get(i).getName();
                   s = s.replaceAll(" ", "+");
                   search += s + "/";
                }
                search = "https://www.google.com/maps/dir/" + search;
                Uri uri = Uri.parse(search);
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                intent.setPackage("com.google.android.apps.maps");
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                search = "";
                for(int i=0; i<finalRoute.size(); i++){
                    String s = finalRoute.get(i).getName();
                    s = s.replaceAll(" ", "+");
                    search += s + "/";
                }
                search = "https://www.google.com/maps/dir/" + search;
                saveTrip();
            }
        });

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleCallback);
        itemTouchHelper.attachToRecyclerView(recyclerView);

    }

    ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper.SimpleCallback(ItemTouchHelper.UP | ItemTouchHelper.DOWN, 0) {
        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            int fromPosition = viewHolder.getAdapterPosition();
            int toPosition = target.getAdapterPosition();
            Collections.swap(chosenRoute, fromPosition, toPosition);
            Collections.swap(finalRoute, fromPosition, toPosition);
            recyclerView.getAdapter().notifyItemMoved(fromPosition, toPosition);
            return false;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {

        }
    };

    public void changeToLocations(){
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("Markers");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                chosenRoute.clear();
                for(Attraction attraction : selectedAttractions){
                    Location location = snapshot.child(attraction.getName()).getValue(Location.class);
                    totalTime += attraction.getTime();
                    chosenRoute.add(location);
                }
                System.out.println("ssssssssssssssssssssssss     " + chosenRoute.size());
                distanceMatrix = generateDistanceMatrix(chosenRoute.size());
                places = new Place[chosenRoute.size()];
                for(int i=0; i<chosenRoute.size(); i++) places[i] = (new Place(i, chosenRoute.get(i)));
                runGreedyAlg();
                for(Location l : chosenRoute){
                    for(Attraction a : selectedAttractions)
                        if(l.getId() == a.getId()) finalRoute.add(a);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }

    public void saveTrip(){

        DialogTrip dialogTrip = new DialogTrip();
        dialogTrip.show(getSupportFragmentManager(), "trip dialog");
    }

    private double[][] generateDistanceMatrix(int dimension){
        double[][] distance = new double[dimension][dimension];

        for(int i=0; i<dimension; i++) {
            for(int j=i; j<dimension; j++) {
                double dist = meterDistanceBetweenPoints(chosenRoute.get(i).getLat(), chosenRoute.get(i).getLng(),
                        chosenRoute.get(j).getLat(), chosenRoute.get(j).getLng());
                distance[i][j] = dist;
                distance[j][i] = dist;
            }
        }
        return distance;
    }

    private double meterDistanceBetweenPoints(double lat_a, double lng_a, double lat_b, double lng_b) {
        float pk = (float) (180.f/Math.PI);

        double a1 = lat_a / pk;
        double a2 = lng_a / pk;
        double b1 = lat_b / pk;
        double b2 = lng_b / pk;

        double t1 = Math.cos(a1) * Math.cos(a2) * Math.cos(b1) * Math.cos(b2);
        double t2 = Math.cos(a1) * Math.sin(a2) * Math.cos(b1) * Math.sin(b2);
        double t3 = Math.sin(a1) * Math.sin(b1);
        double tt = Math.acos(t1 + t2 + t3);

        return 6366000 * tt;
    }

    public void greedy(Individual ind, Place start) {
        ArrayList<Place> newRoute = new ArrayList<Place>(ind.getLocations().length);

        newRoute.add(start);
        Place currentPlace = start;
        Place nextPlace = null;
        double minDistance = Integer.MAX_VALUE;

        while(newRoute.size()!=ind.getLocations().length) {
            for(int i=0; i<ind.getLocations().length; i++) {
                if(!newRoute.contains(ind.getLocations()[i])) {
                    if(distanceMatrix[currentPlace.id][ind.getLocations()[i].id]<minDistance) {
                        minDistance = distanceMatrix[currentPlace.id][ind.getLocations()[i].id];
                        nextPlace = ind.getLocations()[i];
                    }
                }
            }

            if(nextPlace!=null) {
                newRoute.add(nextPlace);
                currentPlace = nextPlace;
                minDistance = Integer.MAX_VALUE;
            }

        }
        ind.setLocations(newRoute);
    }

    public void runGreedyAlg() {
        Individual best = new Individual(places);
        double bestScore = Integer.MAX_VALUE;
        for(int a=0; a<places.length; a++) {
            for(int i=0; i<chosenRoute.size(); i++) places[i] = (new Place(i, chosenRoute.get(i)));
            Individual ind = new Individual(places);
            greedy(ind, ind.getLocations()[a]);
            if(ind.calculateRouteDistance() < bestScore) {
                best = copy(ind);
                bestScore = ind.calculateRouteDistance();
            }
        }
        chosenRoute.clear();
        for (int i=0; i<best.getLocations().length; i++) chosenRoute.add(best.getLocations()[i].location);
    }

    public Individual copy(Individual ind) {
        Individual copyInd;
        Place[] copyPlace = new Place[ind.getLocations().length];
        Place c;
        for(int i=0; i<ind.getLocations().length; i++) {
            c = ind.getLocations()[i];
            Location l = new Location(c.location.getId(), c.location.getName(), c.location.getLat(), c.location.getLng(), c.location.getTag(), c.location.getImage());
            if(c != null)copyPlace[i] = new Place(c.id, l);
        }
        copyInd = new Individual(copyPlace);
        copyInd.setRouteDistance(ind.getRouteDistance());
        return copyInd;
    }

    @Override
    public void finishTripSetUp(String title) {
        String uniqueID = UUID.randomUUID().toString();
        Trip trip = new Trip(uniqueID, title, totalTime, finalRoute, search);
        loadData();
        trips.add(trip);
        saveData();

        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        intent.putExtra(MainActivity.ARG_FRAGMENT_ID, 4);
        startActivity(intent);
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
    }

    class Place {
        public int id;
        public Location location;

        public Place(int i, Location l) {
            this.id = i;
            this.location = l;
        }


    }

    class Individual {

        private Place[] locations;
        private double routeDistance;

        public Individual(Place[] p) {
            /*for(int i=0; i<p.length; i++){
                this.locations[i] = new Place(i, p[i].location);
            }*/
            this.locations=p;
        }

        public double calculateRouteDistance() {
            double total = 0.0;

            for(int i=0; i<locations.length-1; i++) {
                total += distanceMatrix[locations[i].id][locations[i+1].id];
            }
            return total;
        }

        public Place[] getLocations() {
            return locations;
        }

        public void setLocations(Place[] locations) {
            this.locations = locations;
        }

        public void setLocations(ArrayList<Place> places) {
            for(int i=0; i<places.size(); i++)
                this.locations[i] = places.get(i);
        }

        public double getRouteDistance() {
            return routeDistance;
        }

        public void setRouteDistance(double routeDistance) {
            this.routeDistance = routeDistance;
        }
    }

}