package com.example.kudowazdroj.ui.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.example.kudowazdroj.R;
import com.example.kudowazdroj.database.Accommodation;
import com.example.kudowazdroj.database.Attraction;

import java.util.ArrayList;

public class AccommodationAdapter extends BaseAdapter {

    Context context;
    ArrayList<Accommodation> accommodations;
    LayoutInflater inflater;

    public AccommodationAdapter(Context context, ArrayList<Accommodation> accommodations){
        this.context = context;
        this.accommodations = accommodations;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return accommodations.size();
    }

    @Override
    public Object getItem(int position) {
        return accommodations.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View view = inflater.inflate(R.layout.accommodation, null);

        ImageView imageView = (ImageView) view.findViewById(R.id.imageAccommodation);
        //TextView textView = (TextView) view.findViewById(R.id.competitionName);

/*        String s = Utility.getCompetitionLogoFleName(competitions.get(position).getName());

        int resID = context.getResources().getIdentifier(s, "drawable", context.getPackageName());

        imageView.setImageResource(resID);
        textView.setText(competitions.get(position).getName());*/

        imageView.setImageResource(R.drawable.verde);

        return view;

    }
}
