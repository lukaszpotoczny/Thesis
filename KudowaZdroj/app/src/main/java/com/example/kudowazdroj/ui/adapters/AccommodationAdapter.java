package com.example.kudowazdroj.ui.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.kudowazdroj.R;
import com.example.kudowazdroj.database.Accommodation;
import com.example.kudowazdroj.database.Attraction;
import com.squareup.picasso.Picasso;

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
        TextView name = view.findViewById(R.id.accommodation_text_1);
        TextView rating = view.findViewById(R.id.accommodation_text_2);

        name.setText(accommodations.get(position).getName());
        rating.setText(accommodations.get(position).getRating());

        String url = accommodations.get(position).getImage();
        if(!url.equals("")) Picasso.with(context).load(url).into(imageView);

        return view;
    }
}
