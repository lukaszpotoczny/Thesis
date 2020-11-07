package com.example.kudowazdroj.ui.trips;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.example.kudowazdroj.R;
import com.example.kudowazdroj.database.Location;
import com.example.kudowazdroj.ui.adapters.TripPickAdapter;
import com.example.kudowazdroj.ui.attractions.AttractionsActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class AddTripActivity extends AppCompatActivity {

    ArrayList<Location> attractions;
    ArrayList<Location> selectedAttractions;
    TripPickAdapter tripPickAdapterFav, tripPickAdapterRest;

    ListView listFav, listRest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_trip);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

        listFav = findViewById(R.id.listTripPickFav);
        listRest = findViewById(R.id.listTripPick);

        attractions = new ArrayList<>();
        selectedAttractions = new ArrayList<>();

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("Markers");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                attractions.clear();
                for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                    Location location = dataSnapshot.getValue(Location.class);
                    attractions.add(location);
                }
                setAdapters();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });


        Button button = findViewById(R.id.ad_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AddTripActivity.this, TripDetailsActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("attractions", selectedAttractions);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

        listFav.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Location location = attractions.get(i);
                Intent intent = new Intent(getApplicationContext(), AttractionsActivity.class);
                intent.putExtra(AttractionsActivity.ARG_ATTRACTION_KEY, location.getName());
                startActivity(intent);
            }
        });

        listRest.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Location location = attractions.get(i);
                Intent intent = new Intent(getApplicationContext(), AttractionsActivity.class);
                intent.putExtra(AttractionsActivity.ARG_ATTRACTION_KEY, location.getName());
                startActivity(intent);
            }
        });
    }

    public void setAdapters(){
        tripPickAdapterFav = new TripPickAdapter(getApplicationContext(), attractions, selectedAttractions);
        listFav.setAdapter(tripPickAdapterFav);

        tripPickAdapterRest = new TripPickAdapter(getApplicationContext(), attractions, selectedAttractions);
        listRest.setAdapter(tripPickAdapterRest);

        ListUtils.setDynamicHeight(listFav);
        ListUtils.setDynamicHeight(listRest);
    }

    public static class ListUtils {
        public static void setDynamicHeight(ListView mListView) {
            ListAdapter mListAdapter = mListView.getAdapter();
            if (mListAdapter == null) {
                // when adapter is null
                return;
            }
            int height = 0;
            int desiredWidth = View.MeasureSpec.makeMeasureSpec(mListView.getWidth(), View.MeasureSpec.UNSPECIFIED);
            for (int i = 0; i < mListAdapter.getCount(); i++) {
                View listItem = mListAdapter.getView(i, null, mListView);
                listItem.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED);
                height += listItem.getMeasuredHeight();
            }
            ViewGroup.LayoutParams params = mListView.getLayoutParams();
            params.height = height + (mListView.getDividerHeight() * (mListAdapter.getCount() - 1));
            mListView.setLayoutParams(params);
            mListView.requestLayout();
        }
    }
}