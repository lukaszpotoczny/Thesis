package com.example.kudowazdroj.ui.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.example.kudowazdroj.R;
import com.example.kudowazdroj.database.Attraction;
import com.example.kudowazdroj.database.Restaurant;

import java.util.ArrayList;

public class RestaurantsAdapter extends BaseAdapter {

    Context context;
    ArrayList<Restaurant> restaurants;
    LayoutInflater inflater;

    public RestaurantsAdapter(Context context, ArrayList<Restaurant> restaurants){
        this.context = context;
        this.restaurants = restaurants;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return restaurants.size();
    }

    @Override
    public Object getItem(int position) {
        return restaurants.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View view = inflater.inflate(R.layout.restaurant, null);

        ImageView imageView = (ImageView) view.findViewById(R.id.imageRestaurants);
        //TextView textView = (TextView) view.findViewById(R.id.competitionName);

/*        String s = Utility.getCompetitionLogoFleName(competitions.get(position).getName());

        int resID = context.getResources().getIdentifier(s, "drawable", context.getPackageName());

        imageView.setImageResource(resID);
        textView.setText(competitions.get(position).getName());*/

        imageView.setImageResource(R.drawable.restauracja);

        return view;

    }
}
