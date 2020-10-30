package com.example.kudowazdroj.ui.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.kudowazdroj.R;
import com.example.kudowazdroj.database.Attraction;
import com.example.kudowazdroj.database.Restaurant;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

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
        TextView name = view.findViewById(R.id.restaurant_text_1);
        TextView address = view.findViewById(R.id.restaurant_text_2);
        TextView rating = view.findViewById(R.id.restaurant_text_3);

        name.setText(restaurants.get(position).getName());
        address.setText(restaurants.get(position).getAddress());
        rating.setText(restaurants.get(position).getRating());

        String url = restaurants.get(position).getImage();
        if(!url.equals("")) Picasso.with(context).load(url).into(imageView);

        return view;

    }
}
