package com.example.kudowazdroj.ui.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.kudowazdroj.R;
import com.example.kudowazdroj.database.Attraction;
import com.example.kudowazdroj.ui.attractions.AttractionsActivity;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class TripRouteAdapter extends RecyclerView.Adapter<TripRouteAdapter.TripViewHolder> {

    private Context context;
    private ArrayList<Attraction> attractions;

    public TripRouteAdapter(Context context, ArrayList<Attraction> a){
        this.context = context;
        this.attractions = a;
    }

    @NonNull
    @Override
    public TripViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.trip_plan, parent, false);
        TripViewHolder tripViewHolder = new TripViewHolder(view);
        return tripViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull TripViewHolder holder, int position) {
        final Attraction attraction = attractions.get(position);

        holder.title.setText(attraction.getName());
        holder.time.setText(attraction.getTime() + " min");

        String url = attraction.getPhoto();
        if(!url.equals("")) Picasso.with(context).load(url).into(holder.image);

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, AttractionsActivity.class);
                intent.putExtra(AttractionsActivity.ARG_ATTRACTION_KEY, attraction.getName());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return attractions.size();
    }

    public static class TripViewHolder extends RecyclerView.ViewHolder{
        public TextView title;
        public TextView time;
        public ImageView image;
        public CardView cardView;

        public TripViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.tripPlan_text_1);
            time = itemView.findViewById(R.id.tripPlan_text_2);
            image = itemView.findViewById(R.id.imageTripPlan);
            cardView = itemView.findViewById(R.id.cardTripPlan);
        }
    }
}
