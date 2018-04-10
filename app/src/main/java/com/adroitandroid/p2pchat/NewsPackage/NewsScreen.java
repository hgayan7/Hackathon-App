package com.adroitandroid.p2pchat.NewsPackage;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.adroitandroid.p2pchat.R;

import java.util.ArrayList;


public class NewsScreen extends AppCompatActivity {

    MyAdapter myAdapter;
    ArrayList<News> newsList;
    String newsUrl;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view_news);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        newsUrl = "http://newsapi.org/v2/everything?language=en&q=earthquake&flood&landslide&sortBy=relevancy&apiKey=09eabb5b17c24a569f763de3378d28e6";
        newsList = new ArrayList<>();
        myAdapter = new MyAdapter(this, newsList);
        recyclerView.setAdapter(myAdapter);
        new DownloadNewsData(newsList, myAdapter).execute(newsUrl);
    }

    @Override
    protected void onResume() {
        super.onResume();
        new DownloadNewsData(newsList, myAdapter).execute(newsUrl);
    }
}
