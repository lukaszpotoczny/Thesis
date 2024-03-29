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
        final TextView title = (TextView) view.findViewById(R.id.news_text_1);
        final TextView content = (TextView) view.findViewById(R.id.news_text_3);
        TextView date = (TextView) view.findViewById(R.id.news_text_2);

        title.setText(news.get(position).getTitle());
        content.setText(news.get(position).getContent());
        date.setText(news.get(position).getDate());
        String url="";
        if(news.get(position).getImages().size() > 0) {
            url = news.get(position).getImages().get(0);
            if(!url.equals("")) Picasso.with(context).load(url).into(imageView);
        }

        title.post(new Runnable() {
            @Override
            public void run() {
                if(title.getLineCount() > 1) content.setMaxLines(2);
                else content.setMaxLines(3);
            }
        });
        return view;
    }
}
