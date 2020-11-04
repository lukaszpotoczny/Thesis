package com.example.kudowazdroj.ui.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;

import com.example.kudowazdroj.R;
import com.example.kudowazdroj.database.Attraction;
import com.example.kudowazdroj.database.Restaurant;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class TripPickAdapter extends BaseAdapter {

    Context context;
    ArrayList<Attraction> attractions;
    ArrayList<Attraction> selectedAttractions;
    LayoutInflater inflater;

    public TripPickAdapter(Context context, ArrayList<Attraction> attractions, ArrayList<Attraction> selectedAttractions){
        this.context = context;
        this.attractions = attractions;
        this.selectedAttractions = selectedAttractions;
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

        ImageView imageView = (ImageView) view.findViewById(R.id.imageAttractionPick);
        TextView name = view.findViewById(R.id.attractionPick_text_1);
        TextView time = view.findViewById(R.id.attractionPick_text_2);
        final CheckBox checkBox = view.findViewById(R.id.checkBox);

        CardView cardView = view.findViewById(R.id.cardAttractionPick);

        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(checkBox.isChecked()) checkBox.setChecked(false);
                else checkBox.setChecked(true);
            }
        });

        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b) selectedAttractions.add(attractions.get(position));
                else  selectedAttractions.remove(attractions.get(position));
            }
        });

        name.setText(attractions.get(position).getName());
      //  time.setText(attractions.get(position).getName());

        String url = attractions.get(position).getImages().get(0);
        if(!url.equals("")) Picasso.with(context).load(url).into(imageView);

        return view;

    }
}
