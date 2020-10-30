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
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class AttractionsFragment extends Fragment {

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
        attractions.add(new Attraction(1, "ABC", "a"));

        downloadJSON("https://kudowa.pl/get_attractions_list.php");

        return root;
    }

    private void downloadJSON(final String urlWebService) {

        class DownloadJSON extends AsyncTask<Void, Void, String> {
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                String[] data = s.split("<br>nextPart");

                int id=0;
                String name="";
                String photo="";
                attractions.clear();
                for(int i=0; i<data.length-1; i++){
                    if(i%3 == 0) id = Integer.parseInt(data[i]);
                    else if(i%3 == 1) {
                        if(data[i].contains("&#8220;")) {
                            data[i] = data[i].replaceAll("&#8220;", "''");
                            data[i] = data[i].replaceAll("&#8221;", "''");
                        }
                        if(data[i].contains("&#8211;")) data[i] = data[i].replaceAll("&#8211;", "-");
                        name = data[i];
                    }
                    else{
                        photo = data[i];
                        attractions.add(new Attraction(id, name, "", photo));
                    }
                }

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

            }

            @Override
            protected String doInBackground(Void... voids) {
                try {
                    URL url = new URL(urlWebService);
                    HttpURLConnection con = (HttpURLConnection) url.openConnection();
                    StringBuilder sb = new StringBuilder();
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(con.getInputStream()));
                    String json;
                    while ((json = bufferedReader.readLine()) != null) {
                        sb.append(json + "\n");
                    }
                    con.disconnect();
                    return sb.toString().trim();
                } catch (Exception e) {
                    return null;
                }
            }
        }
        DownloadJSON getJSON = new DownloadJSON();
        getJSON.execute();
    }

}
