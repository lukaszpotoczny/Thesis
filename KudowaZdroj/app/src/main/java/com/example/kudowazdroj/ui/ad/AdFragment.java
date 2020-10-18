package com.example.kudowazdroj.ui.ad;

import android.content.Intent;
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
import com.example.kudowazdroj.database.Ad;
import com.example.kudowazdroj.database.Attraction;
import com.example.kudowazdroj.ui.adapters.AdAdapter;
import com.example.kudowazdroj.ui.adapters.AttractionsAdapter;
import com.example.kudowazdroj.ui.attractions.AttractionsActivity;
import com.example.kudowazdroj.ui.restaurants.RestaurantsFragment;

import java.util.ArrayList;

public class AdFragment extends Fragment {

    ArrayList<Ad> ads;
    AdAdapter adAdapter;
    GridView gridView;

    Button addButton;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.ad_fragment, container, false);

        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle(R.string.nav_menu_6);

        gridView = root.findViewById(R.id.gridAd);
        ads = new ArrayList<>();
        // competitions = FootballManiaContract.getDbHelperInstance(getContext()).getCompetitionsList();

        ads.add(new Ad(1, "ABC", "a", "a"));
        ads.add(new Ad(1, "ABC", "a", "a"));
        ads.add(new Ad(1, "ABC", "a", "a"));
        ads.add(new Ad(1, "ABC", "a", "a"));
        ads.add(new Ad(1, "ABC", "a", "a"));
        ads.add(new Ad(1, "ABC", "a", "a"));
        ads.add(new Ad(1, "ABC", "a", "a"));


        adAdapter = new AdAdapter(getActivity().getApplicationContext(), ads);
        gridView.setAdapter(adAdapter);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Ad ad = ads.get(i);
                Intent intent = new Intent(getActivity(), AdActivity.class);
                intent.putExtra(AdActivity.ARG_AD_ID, ad.getId());
                startActivity(intent);

              //  getFragmentManager().beginTransaction().replace(R.id.fragment_container, new SingleAdFragment()).commit();
            }
        });

        addButton = root.findViewById(R.id.ad_button);

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), AddAdActivity.class);
                startActivity(intent);
            }
        });

        return root;
    }
}
