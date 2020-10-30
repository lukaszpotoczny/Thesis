package com.example.kudowazdroj.ui.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.kudowazdroj.R;
import com.example.kudowazdroj.database.Ad;
import com.example.kudowazdroj.database.News;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

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

        TextView title = view.findViewById(R.id.ad_text_1);
        TextView dateText = view.findViewById(R.id.ad_text_2);
        TextView content = view.findViewById(R.id.ad_text_3);
        TextView author = view.findViewById(R.id.ad_text_4);
        TextView contact = view.findViewById(R.id.ad_text_5);

        title.setText(ads.get(position).getTitle());
        content.setText(ads.get(position).getContent());
        author.setText(ads.get(position).getAuthor());
        contact.setText(ads.get(position).getContact());

        String stringDate = ads.get(position).getDate();
        SimpleDateFormat format = new SimpleDateFormat("E MMM d HH:mm:ss z yyyy", Locale.getDefault());
        try {
            Date date = format.parse(stringDate);
            SimpleDateFormat df = new SimpleDateFormat("dd.MM.yyyy", Locale.getDefault());
            String formattedDate = df.format(date);
            dateText.setText(formattedDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return view;
    }
}
