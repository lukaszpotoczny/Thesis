package com.example.kudowazdroj.ui.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.example.kudowazdroj.R;
import com.example.kudowazdroj.database.Ad;
import com.example.kudowazdroj.database.News;

import java.util.ArrayList;

public class AdAdapter extends BaseAdapter {

    Context context;
    ArrayList<Ad> ads;
    LayoutInflater inflater;

    public AdAdapter(Context context, ArrayList<Ad> ads){
        this.context = context;
        this.ads = ads;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return ads.size();
    }

    @Override
    public Object getItem(int position) {
        return ads.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View view = inflater.inflate(R.layout.ad, null);

        return view;

    }
}
