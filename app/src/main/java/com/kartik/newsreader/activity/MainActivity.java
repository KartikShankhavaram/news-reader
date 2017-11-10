package com.kartik.newsreader.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.kartik.newsreader.R;
import com.kartik.newsreader.adapter.NewsAdapter;
import com.kartik.newsreader.data.NewsInfo;
import com.kartik.newsreader.data.PublicationInfo;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.recycler_view) RecyclerView recyclerView;
    @BindView(R.id.my_toolbar) Toolbar toolbar;

    NewsAdapter newsAdapter;
    ArrayList<NewsInfo> newsInfoList = new ArrayList<>();
    NewsInfo newsInfo;
    URL url2;
    HttpURLConnection urlConnection;
    int index = 0;
    int listSize = 30;
    int currentProgress = 0;
    SharedPreferences sharedPreferences;
    ArrayList<PublicationInfo> sources;
    ArrayList pref;

    @SuppressLint("StaticFieldLeak")
    /*public class GetNews extends AsyncTask<String, Integer, String> {

        @Override
        protected String doInBackground(String... urls) {
            try {
                url2 = new URL(urls[0]);
                urlConnection = (HttpURLConnection) url2.openConnection();

                InputStream in = urlConnection.getInputStream();
                InputStreamReader reader = new InputStreamReader(in);

                publishProgress(++currentProgress);

                String result = "";
                int data = reader.read();
                char current;

                while(data != -1) {

                    current = (char) data;
                    result += current;
                    data = reader.read();

                }
                return result;



            } catch (java.io.IOException e) {
                return null;
            }
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            try {
                newsInfo = new NewsInfo();
                JSONObject newsJSON = new JSONObject(s);
                newsInfo.setTitle(newsJSON.getString("title"));
                newsInfo.setUrl(newsJSON.getString("url"));
                newsInfo.setAuthor(newsJSON.getString("by"));

            } catch (JSONException e) {
                e.printStackTrace();
            }
            newsInfoList.add(index, newsInfo);
            index++;
            if(newsInfoList != null && newsInfoList.size() == listSize) {
                findViewById(R.id.loadingPane1).setVisibility(View.GONE);
                newsAdapter = new NewsAdapter(newsInfoList, getApplicationContext());
                recyclerView.setAdapter(newsAdapter);
                findViewById(R.id.recycler_view).setVisibility(View.VISIBLE);
            }

        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            progressBar.setProgress(values[0]);
        }
    }*/


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        setSupportActionBar(toolbar);

        Intent intentFromSources = getIntent();

        if(intentFromSources != null) {

        }

        recyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setVisibility(View.GONE);

        sharedPreferences = getSharedPreferences(getPackageName(), MODE_PRIVATE);
        if(sharedPreferences.getString("sources_pref", null) == null) {
            Toast.makeText(getApplicationContext(), "Please select atleast 1 news source", Toast.LENGTH_LONG).show();
            startActivity(new Intent(this, PublicationSelectionActivity.class));
        }



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch(id) {
            case R.id.menu_settings:
                startActivity(new Intent(this, SettingsActivity.class));
                return true;

            case R.id.select_publ:
                startActivity(new Intent(this, PublicationSelectionActivity.class).putExtra("status", 1));

            default:
                return false;

        }
    }
}
