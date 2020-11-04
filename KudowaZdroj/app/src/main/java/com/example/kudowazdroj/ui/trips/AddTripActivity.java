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
import android.widget.GridView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.kudowazdroj.R;
import com.example.kudowazdroj.database.Attraction;
import com.example.kudowazdroj.database.Restaurant;
import com.example.kudowazdroj.ui.adapters.TripPickAdapter;
import com.example.kudowazdroj.ui.attractions.AttractionsActivity;
import com.example.kudowazdroj.ui.restaurants.RestaurantsActivity;
import com.google.android.gms.maps.model.LatLng;


import java.util.ArrayList;
import java.util.Collections;

public class AddTripActivity extends AppCompatActivity {

    ArrayList<Attraction> attractions;
    ArrayList<Attraction> selectedAttractions;
    TripPickAdapter tripPickAdapterFav, tripPickAdapterRest;
   // GridView gridFav, gridRest;
    ListView listFav, listRest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_trip);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

        listFav = findViewById(R.id.listTripPickFav);
    //    gridRest = findViewById(R.id.gridTripPick);
        listRest = findViewById(R.id.listTripPick);
        attractions = new ArrayList<>();
        selectedAttractions = new ArrayList<>();
        String photo = ("https://firebasestorage.googleapis.com/v0/b/kudowazdroj-4b96c.appspot.com/o/Accommodation%2Fverde_montana_wellness_and_spa.jpg?alt=media&token=554ec766-ef98-4659-9908-b7a86cce8e15");

        attractions.add(new Attraction(1, "Kaplica czaszek1", "c", photo));
        attractions.add(new Attraction(1, "Kaplica czaszek2", "c", photo));
        attractions.add(new Attraction(1, "Kaplica czaszek3", "c", photo));
        attractions.add(new Attraction(1, "Kaplica czaszek4", "c", photo));
        attractions.add(new Attraction(1, "Kaplica czaszek5", "c", photo));
        attractions.add(new Attraction(1, "Kaplica czaszek6", "c", photo));
        attractions.add(new Attraction(1, "Kaplica czaszek7", "c", photo));
        attractions.add(new Attraction(1, "Kaplica czaszek8", "c", photo));


        tripPickAdapterFav = new TripPickAdapter(getApplicationContext(), attractions, selectedAttractions);
        listFav.setAdapter(tripPickAdapterFav);

        listFav.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                System.out.println("aaaaaaaaaaaaaaaaaaaaaaaaaaa");
                Attraction attraction = attractions.get(i);
                Intent intent = new Intent(getApplicationContext(), AttractionsActivity.class);
                intent.putExtra(AttractionsActivity.ARG_ATTRACTION_ID, attraction.getId());
                startActivity(intent);
            }
        });

        tripPickAdapterRest = new TripPickAdapter(getApplicationContext(), attractions, selectedAttractions);
        listRest.setAdapter(tripPickAdapterRest);

        listRest.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                System.out.println("aaaaaaaaaaaaaaaaaaaaaaaaaaa");
                Attraction attraction = attractions.get(i);
            }
        });

        ListUtils.setDynamicHeight(listFav);
        ListUtils.setDynamicHeight(listRest);


        final LatLng uno = new LatLng(50.45153483899051, 16.241971850395203);
        final LatLng dos = new LatLng(50.437160074458546, 16.252545453608036);


        Button button = findViewById(R.id.ad_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("wwwwwwwwwwwwwwwwwwwwwwwwww");
                for(int a=0; a<selectedAttractions.size(); a++) System.out.println(selectedAttractions.get(a).getName());

                Intent intent = new Intent(getApplicationContext(), TripDetailsActivity.class);
                startActivity(intent);
            }
        });


/*        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Attraction attraction = attractions.get(i);
                selectedAttractions.add(attraction);
                System.out.println(attraction.getName());

                for(int a=0; a<selectedAttractions.size(); a++) System.out.println(selectedAttractions.get(a).getName());
            }
        });*/



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