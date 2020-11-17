package com.example.kudowazdroj.ui.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

public class AttrAdapter extends RecyclerView.Adapter<AttrAdapter.AttractionViewHolder> {

    private Context context;
    private ArrayList<Attraction> attractions;

    public AttrAdapter(Context context, ArrayList<Attraction> a){
        this.context = context;
        this.attractions = a;
    }

    @NonNull
    @Override
    public AttractionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.attraction, parent, false);
        AttractionViewHolder attractionViewHolder = new AttractionViewHolder(view);
        return attractionViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull AttractionViewHolder holder, int position) {
        final Attraction attraction = attractions.get(position);

        holder.title.setText(attraction.getName());

        String url = attraction.getPhoto();
        if(!url.equals("")) Picasso.with(context).load(url).into(holder.image);

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, AttractionsActivity.class);
                intent.putExtra(AttractionsActivity.ARG_ATTRACTION_KEY, attraction.getName());
                view.getContext().startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return attractions.size();
    }

    public static class AttractionViewHolder extends RecyclerView.ViewHolder{
        public TextView title;
        public ImageView image;
        public CardView cardView;

        public AttractionViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.textAttraction);
            image = itemView.findViewById(R.id.imageAttraction);
            cardView = itemView.findViewById(R.id.cardAttractions0);
        }
    }
}
