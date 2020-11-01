package com.example.kudowazdroj.ui.accommodation;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.kudowazdroj.R;
import com.example.kudowazdroj.database.Accommodation;
import com.example.kudowazdroj.database.Restaurant;
import com.example.kudowazdroj.ui.adapters.AccommodationAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;

public class AccommodationFragment extends Fragment {

    ArrayList<Accommodation> accommodations;
    AccommodationAdapter accommodationAdapter;
    GridView gridView;
    ProgressBar progressBar;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.accommodation_fragment, container, false);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle(R.string.nav_menu_8);

        gridView = root.findViewById(R.id.gridAccommodation);
        progressBar = root.findViewById(R.id.progressBar4);
        accommodations = new ArrayList<>();

        accommodationAdapter = new AccommodationAdapter(getActivity().getApplicationContext(), accommodations);
        gridView.setAdapter(accommodationAdapter);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Accommodation accommodation = accommodations.get(i);
                Intent intent = new Intent(getActivity(), AccommodationActivity.class);
                intent.putExtra(AccommodationActivity.ARG_ACCOMMODATION_KEY, accommodation.getKey());
                startActivity(intent);
            }
        });

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("Accommodation");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                accommodations.clear();
                for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                    Accommodation accommodation = dataSnapshot.getValue(Accommodation.class);
                    String key = dataSnapshot.getKey();
                    accommodation.setKey(key);
                    accommodations.add(accommodation);
                    progressBar.setVisibility(View.INVISIBLE);
                }
                Collections.sort(accommodations);
                accommodationAdapter.notifyDataSetChanged();
                progressBar.setVisibility(View.INVISIBLE);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });

        return root;
    }
}
