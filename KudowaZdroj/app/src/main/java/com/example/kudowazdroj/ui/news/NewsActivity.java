package com.example.kudowazdroj.ui.news;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kudowazdroj.MainActivity;
import com.example.kudowazdroj.R;
import com.squareup.picasso.Picasso;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;

public class NewsActivity extends AppCompatActivity {

    public static final String ARG_NEWS_ID = "id";
    TextView title;
    TextView content;
    TextView date;
    ImageView[] imageViews;
    ProgressBar progressBar;
    CardView cardView;

    ArrayList <String> images;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

        title = findViewById(R.id.news_title);
        content = findViewById(R.id.news_text_1);
        date = findViewById(R.id.news_date);
        imageViews = new ImageView[5];
        imageViews[0] = findViewById(R.id.news_image_1);
        imageViews[1] = findViewById(R.id.news_image_2);
        imageViews[2] = findViewById(R.id.news_image_3);
        imageViews[3] = findViewById(R.id.news_image_4);
        imageViews[4] = findViewById(R.id.news_image_5);
        progressBar = findViewById(R.id.newsProgressBar2);
        cardView = findViewById(R.id.cardNewsGoBack);

        images = new ArrayList<String>();


        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        if(checkConnection()) {
            downloadJSON("https://kudowa.pl/get_news_photo.php");
            downloadJSON("https://kudowa.pl/get_news.php");
        }


    }

    private boolean checkConnection(){
        ConnectivityManager cm = (ConnectivityManager)getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return (activeNetwork != null && activeNetwork.isConnectedOrConnecting());
    }

    private void downloadJSON(final String urlWebService) {

        class DownloadJSON extends AsyncTask<Void, Void, String> {

            public String fixContent(String s){
                System.out.println(s);
                s = s.replaceAll("[\\[\\]]","SPLIT_PLACE");
                String data[] = s.split("SPLIT_PLACE");
                String result = "";
                for(int i=0; i<data.length; i++){
                    if(i%2 == 0) result += data[i];
                }

                result = result.replaceAll("This is a custom heading element.", "");
                result = result.replaceAll("PDF\\)", "PDF) na stronie kudowa.pl");
                return result;
            }

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }


            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                if(urlWebService.equals("https://kudowa.pl/get_news_photo.php")){
                    if(!s.equals("")) images.add(s);
                    return;
                }
                String[] data = s.split("<br>nextPart");
                title.setText(data[0]);
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                    content.setText(Html.fromHtml(fixContent(data[1]), Html.FROM_HTML_MODE_LEGACY));
                } else {
                    content.setText(Html.fromHtml(fixContent(data[1])));
                }
                content.setMovementMethod(LinkMovementMethod.getInstance());
                date.setText(data[2].substring(0, 10));

                for(int i=7; i<data.length; i=i+4){
                   if(data[i].contains(".jpg")) {
                       if(!data[i].contains("https")) {
                           data[i] = data[i].replaceAll("http", "https");
                       }
                       if(!images.contains(data[i])) images.add(data[i]);
                   }
                }

                for(int i=0; i<imageViews.length && i<images.size(); i++){
                    Picasso.with(getApplicationContext()).load(images.get(i)).into(imageViews[i]);
                    imageViews[i].setVisibility(View.VISIBLE);
                }

                progressBar.setVisibility(View.INVISIBLE);
            }

            @Override
            protected String doInBackground(Void... voids) {
                Bundle extras = getIntent().getExtras();
                int newsID = extras.getInt(ARG_NEWS_ID);
                try {
                    URL url = new URL(urlWebService);
                    HttpURLConnection con = (HttpURLConnection) url.openConnection();
                    con.setRequestMethod("POST");
                    con.setDoOutput(true);
                    con.setDoInput(true);
                    OutputStream outputStream = con.getOutputStream();
                    BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                    String post_data = URLEncoder.encode("newsID", "UTF-8") + "=" + URLEncoder.encode(String.valueOf(newsID), "UTF-8");
                    bufferedWriter.write(post_data);
                    bufferedWriter.flush();
                    bufferedWriter.close();
                    outputStream.close();
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