package com.example.kudowazdroj.ui.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

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
    ArrayList<Location> attractions;
    ArrayList<Location> selectedAttractions;
    LayoutInflater inflater;

    public TripPickAdapter(Context context, ArrayList<Location> attractions, ArrayList<Location> selectedAttractions){
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
        final TextView time = view.findViewById(R.id.attractionPick_text_2);
        final CheckBox checkBox = view.findViewById(R.id.checkBox);

/*        CardView cardView = view.findViewById(R.id.cardAttractionPick);

        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("aaaaaaaaaaaaaaaaaaaaaaaaaaa");
                Location location = attractions.get(position);
                Intent intent = new Intent(context, AttractionsActivity.class);
                intent.putExtra(AttractionsActivity.ARG_ATTRACTION_KEY, location.getName());
                startActivity(intent);
            }
        });*/

        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b) selectedAttractions.add(attractions.get(position));
                else  selectedAttractions.remove(attractions.get(position));
            }
        });

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("Attractions").child(attractions.get(position).getName());
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                time.setText(snapshot.child("time").getValue().toString() + " min");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });

        name.setText(attractions.get(position).getName());

        String url = attractions.get(position).getImage();
        if(!url.equals("")) Picasso.with(context).load(url).into(imageView);

        return view;

    }
}
