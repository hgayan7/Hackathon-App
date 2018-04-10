package com.adroitandroid.p2pchat.NewsPackage;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;


public class DownloadNewsData extends AsyncTask<String,Void,String> {

    String url;
    private List<News> newsList;
    MyAdapter customNewsAdapter;

    public DownloadNewsData(List<News> newsList, MyAdapter customNewsAdapter) {
        this.newsList = newsList;
        this.customNewsAdapter = customNewsAdapter;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected String doInBackground(String... urls) {
        StringBuilder result = new StringBuilder();
        URL url;
        HttpURLConnection urlConnection = null;

        try {
            url = new URL(urls[0]);
            urlConnection = (HttpURLConnection) url.openConnection();

            InputStream inputStream = urlConnection.getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);

            int data;
            while( (data = inputStreamReader.read()) != -1){
                char current = (char) data;
                result.append(current);
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            urlConnection.disconnect();
        }
        return result.toString();
    }

    @Override
    protected void onPostExecute(String response) {
        super.onPostExecute(response);

        try {
            JSONObject jObject = new JSONObject(response);
            JSONArray jsonarray = new JSONArray(jObject.getString("articles"));

            Log.i("debug", "json length" + String.valueOf(jsonarray.length()));

            for (int i = 0; i < jsonarray.length(); i++) {

                JSONObject jsonobject = jsonarray.getJSONObject(i);
                String title = jsonobject.getString("title");
                String urlImage = jsonobject.getString("urlToImage");
                String content = jsonobject.getString("description");
                String time = jsonobject.getString("publishedAt");
                String source = new JSONObject(jsonobject.getString("source")).getString("name");

                News news_data = new News(urlImage, title, content, source, time);
                newsList.add(news_data);
                customNewsAdapter.notifyDataSetChanged();
            }
        } catch (JSONException e) {
            e.printStackTrace();
            Log.i("debug", e.toString());
        }
    }

}
