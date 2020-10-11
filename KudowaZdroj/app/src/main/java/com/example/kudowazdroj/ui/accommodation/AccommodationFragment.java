package com.example.kudowazdroj.ui.accommodation;

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
import com.example.kudowazdroj.database.Accommodation;
import com.example.kudowazdroj.ui.adapters.AccommodationAdapter;

import java.util.ArrayList;

public class AccommodationFragment extends Fragment {

    ArrayList<Accommodation> accommodations;
    AccommodationAdapter accommodationAdapter;
    GridView gridView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.accommodation_fragment, container, false);

        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle(R.string.nav_menu_8);

        gridView = root.findViewById(R.id.gridAccommodation);
        accommodations = new ArrayList<>();
        // competitions = FootballManiaContract.getDbHelperInstance(getContext()).getCompetitionsList();

        accommodations.add(new Accommodation(1, "ABC"));
        accommodations.add(new Accommodation(1, "ABC"));
        accommodations.add(new Accommodation(1, "ABC"));
        accommodations.add(new Accommodation(1, "ABC"));
        accommodations.add(new Accommodation(1, "ABC"));
        accommodations.add(new Accommodation(1, "ABC"));
        accommodations.add(new Accommodation(1, "ABC"));
        accommodations.add(new Accommodation(1, "ABC"));

        accommodationAdapter = new AccommodationAdapter(getActivity().getApplicationContext(), accommodations);
        gridView.setAdapter(accommodationAdapter);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Accommodation accommodation = accommodations.get(i);
                Intent intent = new Intent(getActivity(), AccommodationActivity.class);
                intent.putExtra(AccommodationActivity.ARG_ACCOMMODATION_ID, accommodation.getId());
                startActivity(intent);
            }
        });

        return root;
    }
}
