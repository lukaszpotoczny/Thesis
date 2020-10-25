package com.example.kudowazdroj.ui.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.kudowazdroj.R;
import com.example.kudowazdroj.database.News;
import com.example.kudowazdroj.database.Restaurant;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class NewsAdapter extends BaseAdapter {

    Context context;
    ArrayList<News> news;
    LayoutInflater inflater;

    public NewsAdapter(Context context, ArrayList<News> news){
        this.context = context;
        this.news = news;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return news.size();
    }

    @Override
    public Object getItem(int position) {
        return news.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View view = inflater.inflate(R.layout.news, null);

        ImageView imageView = (ImageView) view.findViewById(R.id.imageNews);
        TextView title = (TextView) view.findViewById(R.id.news_text_1);
        TextView content = (TextView) view.findViewById(R.id.news_text_3);

/*        String s = Utility.getCompetitionLogoFleName(competitions.get(position).getName());

        int resID = context.getResources().getIdentifier(s, "drawable", context.getPackageName());

        imageView.setImageResource(resID);*/
        title.setText(news.get(position).getTitle());
        content.setText(news.get(position).getContent());
        String url="";
        if(news.get(position).getImages().size() > 0) {
            url = news.get(position).getImages().get(0);
            Picasso.with(context).load(url).into(imageView);
        }

        //imageView.setImageResource(R.drawable.news);

        return view;

    }
}
