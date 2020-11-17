package com.example.kudowazdroj.ui.trips;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.example.kudowazdroj.MainActivity;
import com.example.kudowazdroj.R;
import com.example.kudowazdroj.database.Attraction;
import com.example.kudowazdroj.database.Location;
import com.example.kudowazdroj.ui.adapters.TripPickAdapter;
import com.example.kudowazdroj.ui.attractions.AttractionsActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;

public class AddTripActivity extends AppCompatActivity {

    ArrayList<Attraction> favourites;
    ArrayList<Attraction> test;
    ArrayList<Attraction> rest;
    ArrayList<Attraction> selectedAttractions;
    ArrayList<Attraction> attractions;

    TripPickAdapter tripPickAdapterFav, tripPickAdapterRest;

    ListView listFav, listRest;
    ProgressBar progressBar;
    ImageView buttonCancel, buttonConfirm, buttonConfirmGray;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_trip);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

        listFav = findViewById(R.id.listTripPickFav);
        listRest = findViewById(R.id.listTripPick);
        progressBar = findViewById(R.id.progressBar);
        buttonCancel = findViewById(R.id.imageCancel);
        buttonConfirm = findViewById(R.id.imageConfirm);
        buttonConfirmGray = findViewById(R.id.imageConfirmGray);

        test = new ArrayList<>();
        favourites = new ArrayList<>();
        rest = new ArrayList<>();
        attractions = new ArrayList<>();
        selectedAttractions = new ArrayList<>();

        loadData();

        tripPickAdapterFav = new TripPickAdapter(getApplicationContext(), test, selectedAttractions, buttonConfirm, buttonConfirmGray);
        tripPickAdapterRest = new TripPickAdapter(getApplicationContext(), rest, selectedAttractions, buttonConfirm, buttonConfirmGray);


        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("Attractions");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                attractions.clear();
                for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                    Attraction attraction = dataSnapshot.getValue(Attraction.class);
                    rest.add(attraction);
                    attractions.add(attraction);
                }
                Collections.sort(attractions);
                setLists();
                setAdapters();
                progressBar.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });

        buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        buttonConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AddTripActivity.this, TripDetailsActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("attractions", selectedAttractions);
                intent.putExtras(bundle);

                BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
                    @Override
                    public void onReceive(Context arg0, Intent intent) {
                        String action = intent.getAction();
                        if (action.equals("finish_activity")) {
                            finish();
                        }
                    }
                };
                registerReceiver(broadcastReceiver, new IntentFilter("finish_activity"));
                startActivity(intent);
            }
        });

        listFav.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Attraction attraction = test.get(i);
                Intent intent = new Intent(getApplicationContext(), AttractionsActivity.class);
                intent.putExtra(AttractionsActivity.ARG_ATTRACTION_KEY, attraction.getName());
                startActivity(intent);
            }
        });

        listRest.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Attraction attraction = rest.get(i);
                Intent intent = new Intent(getApplicationContext(), AttractionsActivity.class);
                intent.putExtra(AttractionsActivity.ARG_ATTRACTION_KEY, attraction.getName());
                startActivity(intent);
            }
        });


    }

    @Override
    protected void onResume() {
        super.onResume();
        loadData();
        setLists();
        if(selectedAttractions.size()>1){
            buttonConfirmGray.setVisibility(View.GONE);
            buttonConfirm.setVisibility(View.VISIBLE);
        }
        else{
            buttonConfirm.setVisibility(View.GONE);
            buttonConfirmGray.setVisibility(View.VISIBLE);
        }
        setAdapters();
        tripPickAdapterFav.notifyDataSetChanged();
        tripPickAdapterRest.notifyDataSetChanged();
    }

    private void setLists(){
        selectedAttractions.clear();
        test.clear();
        rest.clear();
        rest.addAll(attractions);
        for(int i=0; i<attractions.size(); i++){
            for(Attraction a : favourites){
                if(attractions.get(i).getId() == a.getId() && !favourites.contains(attractions.get(i))){
                    rest.remove(attractions.get(i));
                    test.add(attractions.get(i));
                    break;
                }
            }
        }
    }

    private void loadData() {
        SharedPreferences sharedPreferences = getSharedPreferences("shared preferences", MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString("task list", null);
        Type type = new TypeToken<ArrayList<Attraction>>() {}.getType();
        favourites = gson.fromJson(json, type);
        if (favourites == null) {
            favourites = new ArrayList<>();
        }
    }

    public void setAdapters(){
        listFav.setAdapter(tripPickAdapterFav);
        listRest.setAdapter(tripPickAdapterRest);
        ListUtils.setDynamicHeight(listFav);
        ListUtils.setDynamicHeight(listRest);
    }

    public static class ListUtils {
        public static void setDynamicHeight(ListView mListView) {
            ListAdapter mListAdapter = mListView.getAdapter();
            if (mListAdapter == null) {
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