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

import java.util.ArrayList;

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
        // competitions = FootballManiaContract.getDbHelperInstance(getContext()).getCompetitionsList();

        restaurants.add(new Restaurant(1, "ABC"));
        restaurants.add(new Restaurant(1, "ABC"));
        restaurants.add(new Restaurant(1, "ABC"));
        restaurants.add(new Restaurant(1, "ABC"));
        restaurants.add(new Restaurant(1, "ABC"));
        restaurants.add(new Restaurant(1, "ABC"));
        restaurants.add(new Restaurant(1, "ABC"));

        restaurantsAdapter = new RestaurantsAdapter(getActivity().getApplicationContext(), restaurants);
        gridView.setAdapter(restaurantsAdapter);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Restaurant restaurant = restaurants.get(i);
                Intent intent = new Intent(getActivity(), RestaurantsActivity.class);
                intent.putExtra(RestaurantsActivity.ARG_RESTAURANT_ID, restaurant.getId());
                startActivity(intent);
            }
        });

        return root;
        
    }
}
