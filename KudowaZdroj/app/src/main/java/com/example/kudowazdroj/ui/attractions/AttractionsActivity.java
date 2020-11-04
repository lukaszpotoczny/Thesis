package com.example.kudowazdroj.ui.attractions;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

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

public class AttractionsActivity extends AppCompatActivity {

    public static final String ARG_ATTRACTION_ID = "id";
    public static final String ARG_ATTRACTION_PHOTO = "photo";
    TextView title, content;
    ImageView titleImage;
    ImageView[] imageViews;
    ProgressBar progressBar;
    String photo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attractions);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

        title = findViewById(R.id.attr_name);
        content = findViewById(R.id.attr_text_1);
        titleImage = findViewById(R.id.attr_image_1);

        imageViews = new ImageView[6];
        imageViews[0] = findViewById(R.id.attr_image_2);
        imageViews[1] = findViewById(R.id.attr_image_3);
        imageViews[2] = findViewById(R.id.attr_image_4);
        imageViews[3] = findViewById(R.id.attr_image_5);
        imageViews[4] = findViewById(R.id.attr_image_6);
        imageViews[5] = findViewById(R.id.attr_image_7);

        progressBar = findViewById(R.id.newsProgressBar2);

        CardView cardView = findViewById(R.id.cardAttractionsGoBack);
        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


        downloadJSON("https://kudowa.pl/get_attraction.php");


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
                result = result.replaceAll("\n\n\n\n", "\n\n");
                result = result.replaceAll("\n\n\n", "\n\n");
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
                title.setText(data[0]);
                content.setText(fixContent(data[1]));

                ArrayList<String> images = new ArrayList<String>();
                for(int i=5; i<data.length; i+=3){
                    if(data[i].contains(".jpg") || data[i].contains(".png")) {
                        if(!data[i].contains("https")) {
                            data[i] = data[i].replaceAll("http", "https");
                        }
                        images.add(data[i]);
                    }
                }
                Picasso.with(getApplicationContext()).load(photo).into(titleImage);
                for(int i=0; i<imageViews.length && i<images.size(); i++){
                    Picasso.with(getApplicationContext()).load(images.get(i)).into(imageViews[i]);
                    imageViews[i].setVisibility(View.VISIBLE);
                }

                progressBar.setVisibility(View.INVISIBLE);
            }

            @Override
            protected String doInBackground(Void... voids) {
                Bundle extras = getIntent().getExtras();
                int newsID = extras.getInt(ARG_ATTRACTION_ID);
                photo = extras.getString(ARG_ATTRACTION_PHOTO);
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