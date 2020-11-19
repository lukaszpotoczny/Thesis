package com.example.kudowazdroj.ui.attractions;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.kudowazdroj.ui.adapters.AttractionsAdapter;
import com.example.kudowazdroj.R;
import com.example.kudowazdroj.database.Attraction;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;

public class AttractionsFragment extends Fragment {

    ArrayList<Attraction> attractions;
  //  AttractionsAdapter attractionsAdapter;
  //  GridView gridView;
    ProgressBar progressBar;

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.attractions_fragment, container, false);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle(R.string.nav_menu_3);

       // gridView = root.findViewById(R.id.gridAttractions);
        progressBar = root.findViewById(R.id.progressBar);
        recyclerView = root.findViewById(R.id.recyclerAttraction);

        attractions = new ArrayList<>();

/*        attractionsAdapter = new AttractionsAdapter(getActivity().getApplicationContext(), attractions);
        gridView.setAdapter(attractionsAdapter);*/

        adapter = new AttractionsAdapter(getContext(), attractions);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 2, GridLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setAdapter(adapter);

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("Attractions");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                attractions.clear();
                for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                    Attraction attraction = dataSnapshot.getValue(Attraction.class);
                    attractions.add(attraction);
                }
                Collections.sort(attractions);
                adapter.notifyDataSetChanged();
                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                progressBar.setVisibility(View.GONE);
            }
        });

/*        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Attraction attraction = attractions.get(i);
                Intent intent = new Intent(getActivity(), AttractionsActivity.class);
                intent.putExtra(AttractionsActivity.ARG_ATTRACTION_KEY, attraction.getName());
                startActivity(intent);
            }
        });*/


        return root;
    }

}
