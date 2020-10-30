package com.example.kudowazdroj.ui.news;

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

import com.example.kudowazdroj.LoadingDialog;
import com.example.kudowazdroj.R;
import com.example.kudowazdroj.database.News;
import com.example.kudowazdroj.ui.adapters.NewsAdapter;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class NewsFragment extends Fragment {

    ArrayList<News> news;
    ArrayList<ImageInfo> images;
    NewsAdapter newsAdapter;
    GridView gridView;
    final LoadingDialog loadingDialog = new LoadingDialog();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.news_fragment, container, false);

        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle(R.string.nav_menu_2);

        gridView = root.findViewById(R.id.gridNews);

        news = new ArrayList<>();
        images = new ArrayList<>();
        news.add(new News(1, "ABC", "a"));

        loadingDialog.show(getFragmentManager(), "elo");
        downloadJSON("https://kudowa.pl/get_news_list.php");
        downloadJSON("https://kudowa.pl/get_images.php");

        return root;
    }

    private void downloadJSON(final String urlWebService) {

        class DownloadJSON extends AsyncTask<Void, Void, String> {

            public String fixContent(String s){
                s = s.replaceAll("[\\[\\]\\<\\>]","SPLIT_PLACE");
                String data[] = s.split("SPLIT_PLACE");
                String result = "";
                for(int i=0; i<data.length; i++){
                    if(i%2 == 0) result += data[i];
                }
                result = result.replaceAll("This is a custom heading element.", "");
                result = result.replaceAll("\n", " ");
                if(result.length() > 67) result = result.substring(0, 64) + "...";
                return result;
            }

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                String[] data = s.split("<br>nextPart");

                if(data[data.length-1].equals("news")){
                    int id=0;
                    String content="";
                    String title="";
                    String date="";
                    news.clear();
                    for(int i=(data.length-2); i>9; i--){
                        if(i%4 == 0) {
                            id = Integer.parseInt(data[i]);
                            news.add(new News(id, title, content, date));
                        }
                        else if(i%4 == 1) {
                            if(data[i].length() > 47) data[i] = data[i].substring(0, 44) + "...";
                            title = data[i];
                        }
                        else if(i%4 == 2) content = fixContent(data[i]);
                        else date = data[i].substring(0, 10);
                    }
                }
                else if(data[data.length-1].equals("images")){
                    int id = 0;
                    String guid = "";
                    for(int i=0; i<data.length-1; i++){
                        if(i%2 == 0) id = Integer.parseInt(data[i]);
                        else if(i%2 == 1) {
                            guid = data[i];
                            if(!guid.contains("https")) guid = guid.replaceAll("http", "https");
                            images.add(new ImageInfo(id, guid));
                        }
                    }
                    for(int i=0; i<news.size(); i++){
                        int j=0;
                        boolean found = false;
                        while(j < images.size() && !found){
                            if(images.get(j).getParentID() == news.get(i).getId()) {
                                news.get(i).getImages().add(images.get(j).getGuid());
                                found = true;
                            }
                            j++;
                        }
                    }

                    loadingDialog.dismiss();
                }


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

    class ImageInfo{
        private int parentID;
        private String guid;

        public ImageInfo(int id, String g){
            this.parentID = id;
            this.guid = g;
        }

        public int getParentID() {
            return parentID;
        }

        public void setParentID(int parentID) {
            this.parentID = parentID;
        }

        public String getGuid() {
            return guid;
        }

        public void setGuid(String guid) {
            this.guid = guid;
        }
    }
}
