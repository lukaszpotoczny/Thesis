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
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class AttractionsAdapter extends BaseAdapter {

    Context context;
    ArrayList<Attraction> attractions;
    LayoutInflater inflater;

    public AttractionsAdapter(Context context, ArrayList<Attraction> attractions){
        this.context = context;
        this.attractions = attractions;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return attractions.size();
    }

    @Override
    public Object getItem(int position) {
        return attractions.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View view = inflater.inflate(R.layout.attraction, null);

        TextView textView = (TextView) view.findViewById(R.id.textAttraction);
        ImageView imageView = (ImageView) view.findViewById(R.id.imageAttraction);

        textView.setText(attractions.get(position).getName());
        String url="";
        if(attractions.get(position).getImages().size() > 0) {
            url = attractions.get(position).getImages().get(0);
            if(!url.equals("")) Picasso.with(context).load(url).into(imageView);
        }

        return view;

    }
}
