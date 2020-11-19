package com.example.kudowazdroj.ui.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;

import com.example.kudowazdroj.R;
import com.example.kudowazdroj.database.Attraction;
import com.example.kudowazdroj.database.Location;
import com.example.kudowazdroj.database.Restaurant;
import com.example.kudowazdroj.ui.attractions.AttractionsActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class TripPickAdapter extends BaseAdapter {

    Context context;
    ArrayList<Attraction> attractions;
    ArrayList<Attraction> selectedAttractions;
    LayoutInflater inflater;
    ImageView confirm, confirmGray;

    public TripPickAdapter(Context context, ArrayList<Attraction> attractions, ArrayList<Attraction> selectedAttractions, ImageView confirm, ImageView confirmGray){
        this.context = context;
        this.attractions = attractions;
        this.selectedAttractions = selectedAttractions;
        this.confirm = confirm;
        this.confirmGray = confirmGray;
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
    public View getView(final int position, View convertView, ViewGroup parent) {
        View view = inflater.inflate(R.layout.attraction_pick, null);

        ImageView imageView = view.findViewById(R.id.imageAttractionPick);
        TextView name = view.findViewById(R.id.attractionPick_text_1);
        TextView time = view.findViewById(R.id.attractionPick_text_2);
        final CheckBox checkBox = view.findViewById(R.id.checkBox);

        if(selectedAttractions.contains(attractions.get(position))) checkBox.setChecked(true);

        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b) {
                    if(selectedAttractions.size() > 24){
                        Toast.makeText(context, "Wybierz maksymalnie 25 atrakcji", Toast.LENGTH_SHORT).show();
                        checkBox.setChecked(false);
                    }
                    else{
                        selectedAttractions.add(attractions.get(position));
                        if(selectedAttractions.size() > 1){
                            confirmGray.setVisibility(View.GONE);
                            confirm.setVisibility(View.VISIBLE);
                        }
                    }
                }
                else  {
                    selectedAttractions.remove(attractions.get(position));
                    if(selectedAttractions.size() < 2){
                        confirm.setVisibility(View.GONE);
                        confirmGray.setVisibility(View.VISIBLE);
                    }
                }
            }
        });

        name.setText(attractions.get(position).getName());
        time.setText(attractions.get(position).getTime() + " min");

        String url = attractions.get(position).getPhoto();
        if(!url.equals("")) Picasso.with(context).load(url).into(imageView);

        return view;
    }
}
