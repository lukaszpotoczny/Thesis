package com.example.kudowazdroj.ui.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.example.kudowazdroj.R;
import com.example.kudowazdroj.database.Attraction;
import com.example.kudowazdroj.database.Trip;

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
/*
        ImageView imageView = (ImageView) view.findViewById(R.id.imageTrip_1);
        //TextView textView = (TextView) view.findViewById(R.id.competitionName);

       String s = Utility.getCompetitionLogoFleName(competitions.get(position).getName());

        int resID = context.getResources().getIdentifier(s, "drawable", context.getPackageName());

        imageView.setImageResource(resID);
        textView.setText(competitions.get(position).getName());

        imageView.setImageResource(R.drawable.kaplica_czaszek);
*/
        return view;

    }
}
