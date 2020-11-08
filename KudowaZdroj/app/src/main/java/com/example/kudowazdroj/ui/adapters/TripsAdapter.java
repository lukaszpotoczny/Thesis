package com.example.kudowazdroj.ui.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.kudowazdroj.R;
import com.example.kudowazdroj.database.Attraction;
import com.example.kudowazdroj.database.Trip;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class TripsAdapter extends BaseAdapter {

    Context context;
    ArrayList<Trip> trips;
    LayoutInflater inflater;

    public TripsAdapter(Context context, ArrayList<Trip> trips){
        this.context = context;
        this.trips = trips;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return trips.size();
    }

    @Override
    public Object getItem(int position) {
        return trips.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = inflater.inflate(R.layout.trip, null);
        Trip trip = trips.get(position);

        TextView title = view.findViewById(R.id.tripTitle);
        TextView duration = view.findViewById(R.id.trips_text_2);
        TextView places = view.findViewById(R.id.trips_text_3);
        ImageView[] imageViews = new ImageView[3];
        imageViews[0] = view.findViewById(R.id.imageTrip_1);
        imageViews[1] = view.findViewById(R.id.imageTrip_2);
        imageViews[2] = view.findViewById(R.id.imageTrip_3);

        title.setText(trip.getName());
        duration.setText(String.valueOf(trip.getDuration()) + " min");
        places.setText(trip.getAttractions().size() + " atrakcje");

        for(int i=0; i<trip.getAttractions().size() && i<imageViews.length; i++){
            Picasso.with(context).load(trip.getAttractions().get(i).getPhoto()).into(imageViews[i]);
            imageViews[i].setVisibility(View.VISIBLE);
        }
        return view;
    }
}
