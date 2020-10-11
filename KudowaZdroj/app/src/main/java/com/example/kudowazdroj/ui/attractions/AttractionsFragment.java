package com.example.kudowazdroj.ui.attractions;

import android.content.Intent;
import android.os.AsyncTask;
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

import com.example.kudowazdroj.ui.adapters.AttractionsAdapter;
import com.example.kudowazdroj.R;
import com.example.kudowazdroj.database.Attraction;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class AttractionsFragment extends Fragment {

    String JSON_STRING;

    ArrayList<Attraction> attractions;
    AttractionsAdapter attractionsAdapter;
    GridView gridView;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.attractions_fragment, container, false);

        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle(R.string.nav_menu_3);

        gridView = root.findViewById(R.id.gridAttractions);
        attractions = new ArrayList<>();
       // competitions = FootballManiaContract.getDbHelperInstance(getContext()).getCompetitionsList();

        attractions.add(new Attraction(1, "ABC"));
        attractions.add(new Attraction(2, "AB2"));
        attractions.add(new Attraction(3, "AB3"));
        attractions.add(new Attraction(3, "AB3"));
        attractions.add(new Attraction(3, "AB3"));

        attractionsAdapter = new AttractionsAdapter(getActivity().getApplicationContext(), attractions);
        gridView.setAdapter(attractionsAdapter);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Attraction attraction = attractions.get(i);
                Intent intent = new Intent(getActivity(), AttractionsActivity.class);
                intent.putExtra(AttractionsActivity.ARG_ATTRACTION_ID, attraction.getId());
                startActivity(intent);
            }
        });

        return root;
    }

    public void getJSON(View view) {
        new BackgroundTask().execute();
    }


    class BackgroundTask extends AsyncTask<Void, Void, String>{

        String json_url;

        @Override
        protected void onPreExecute() {
            //json_url = "http://192.168.56.1/get_data.php";
            json_url = "http://androidtut.comli.com/json_get_data.php";
        }

        @Override
        protected String doInBackground(Void... voids) {
            try {
                URL url = new URL(json_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                StringBuilder stringBuilder = new StringBuilder();
                while((JSON_STRING = bufferedReader.readLine()) != null){
                    stringBuilder.append(JSON_STRING + "\n");
                }
                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();
                return stringBuilder.toString().trim();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;

        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected void onPostExecute(String result) {
          //  TextView textView = (TextView) getView().findViewById(R.id.atrakcjeTxt);
            System.out.println(result);
          //  textView.setText(result);
        }
    }


}
