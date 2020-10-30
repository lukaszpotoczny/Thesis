package com.example.kudowazdroj.ui.restaurants;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.kudowazdroj.R;
import com.example.kudowazdroj.ui.adapters.RestaurantsAdapter;
import com.example.kudowazdroj.database.Restaurant;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Locale;

public class RestaurantsFragment extends Fragment {

    ArrayList<Restaurant> restaurants;
    RestaurantsAdapter restaurantsAdapter;
    GridView gridView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.restaurants_fragment, container, false);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle(R.string.nav_menu_7);

        gridView = root.findViewById(R.id.gridRestaurants);
        restaurants = new ArrayList<>();

        restaurantsAdapter = new RestaurantsAdapter(getActivity().getApplicationContext(), restaurants);
        gridView.setAdapter(restaurantsAdapter);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Restaurant restaurant = restaurants.get(i);
                Intent intent = new Intent(getActivity(), RestaurantsActivity.class);
                intent.putExtra(RestaurantsActivity.ARG_RESTAURANT_KEY, restaurant.getKey());
                startActivity(intent);
            }
        });

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("Restaurants");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                restaurants.clear();
                for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                    Restaurant restaurant = dataSnapshot.getValue(Restaurant.class);
                    String key = dataSnapshot.getKey();
                    restaurant.setKey(key);
                    restaurants.add(restaurant);
                }
                Collections.sort(restaurants);
                restaurantsAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });

        return root;
        
    }


}
