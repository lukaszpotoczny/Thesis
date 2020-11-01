package com.example.kudowazdroj.ui.ad;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.kudowazdroj.R;
import com.example.kudowazdroj.database.Ad;
import com.example.kudowazdroj.database.Attraction;
import com.example.kudowazdroj.database.Restaurant;
import com.example.kudowazdroj.ui.adapters.AdAdapter;
import com.example.kudowazdroj.ui.adapters.AttractionsAdapter;
import com.example.kudowazdroj.ui.attractions.AttractionsActivity;
import com.example.kudowazdroj.ui.restaurants.RestaurantsFragment;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;

public class AdFragment extends Fragment {

    ArrayList<Ad> ads;
    AdAdapter adAdapter;
    GridView gridView;

    Button addButton;
    ProgressBar progressBar;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.ad_fragment, container, false);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle(R.string.nav_menu_6);

        gridView = root.findViewById(R.id.gridAd);
        progressBar = root.findViewById(R.id.progressBar3);
        ads = new ArrayList<>();

        adAdapter = new AdAdapter(getActivity().getApplicationContext(), ads);
        gridView.setAdapter(adAdapter);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Ad ad = ads.get(i);
                Intent intent = new Intent(getActivity(), AdActivity.class);
                intent.putExtra(AdActivity.ARG_AD_ID, ad.getId());
                startActivity(intent);
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

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("Announcement");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ads.clear();
                for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                    Ad ad = dataSnapshot.getValue(Ad.class);
                    if(ad.getTitle().length() > 50) ad.setTitle(ad.getTitle().substring(0, 47) + "...");
                    if(ad.getContent().length() > 80) ad.setContent(ad.getContent().substring(0, 77) + "...");
                    if(ad.getContact().length() > 16) ad.setContact(ad.getContact().substring(0, 13) + "...");
                    if(ad.getAuthor().length() > 16) ad.setAuthor(ad.getAuthor().substring(0, 13) + "...");
                    ads.add(ad);
                }
                Collections.sort(ads);
                adAdapter.notifyDataSetChanged();
                progressBar.setVisibility(View.INVISIBLE);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                progressBar.setVisibility(View.INVISIBLE);
            }
        });

        return root;
    }
}
