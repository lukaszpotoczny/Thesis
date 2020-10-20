package com.example.kudowazdroj.ui.news;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.kudowazdroj.R;
import com.example.kudowazdroj.database.News;
import com.example.kudowazdroj.database.Restaurant;
import com.example.kudowazdroj.ui.adapters.NewsAdapter;
import com.example.kudowazdroj.ui.adapters.RestaurantsAdapter;
import com.example.kudowazdroj.ui.restaurants.RestaurantsActivity;

import java.util.ArrayList;

public class NewsFragment extends Fragment {

    ArrayList<News> news;
    NewsAdapter newsAdapter;
    GridView gridView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.news_fragment, container, false);

        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle(R.string.nav_menu_2);

        gridView = root.findViewById(R.id.gridNews);
        news = new ArrayList<>();
        // competitions = FootballManiaContract.getDbHelperInstance(getContext()).getCompetitionsList();

        news.add(new News(1, "ABC", "a"));
        news.add(new News(1, "ABC", "a"));
        news.add(new News(1, "ABC", "a"));
        news.add(new News(1, "ABC", "a"));
        news.add(new News(1, "ABC", "a"));
        news.add(new News(1, "ABC", "a"));
        news.add(new News(1, "ABC", "a"));
        news.add(new News(1, "ABC", "a"));


        newsAdapter = new NewsAdapter(getActivity().getApplicationContext(), news);
        gridView.setAdapter(newsAdapter);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                News n = news.get(i);
                Intent intent = new Intent(getActivity(), NewsActivity.class);
                intent.putExtra(NewsActivity.ARG_NEWS_ID, n.getId());
                startActivity(intent);
            }
        });

        return root;
    }
}
