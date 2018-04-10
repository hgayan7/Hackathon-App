package com.adroitandroid.p2pchat.MapsPackage;

import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.Toast;

import com.adroitandroid.p2pchat.LocationTracker;
import com.adroitandroid.p2pchat.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.tbruyelle.rxpermissions2.RxPermissions;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class MapsMain extends AppCompatActivity implements OnMapReadyCallback {

    private static final int LOCATION_REQUEST = 500;
    private static  final  String MAP_VIEW_BUNDLE_KEY = "MapViewBundleKey";
    MapView mapView;
    private GoogleMap mMap;

    double latitude, longitude;
    LocationTracker locationTracker;
    ArrayList<LatLng> listPoints;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps_main);

        Bundle mapViewBundle = null;

        if(savedInstanceState != null){
            mapViewBundle = savedInstanceState.getBundle(MAP_VIEW_BUNDLE_KEY);
        }

        mapView = (MapView) findViewById(R.id.map_view);
        mapView.onCreate(mapViewBundle);
        mapView.getMapAsync(this);
        listPoints = new ArrayList<>();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        mMap = googleMap;

        RxPermissions rxPermissions=new RxPermissions(this);
        rxPermissions.request(android.Manifest.permission.ACCESS_FINE_LOCATION, android.Manifest.permission.ACCESS_NETWORK_STATE,
                android.Manifest.permission.ACCESS_COARSE_LOCATION)
                .subscribe(granted-> {
                    if(granted){
                        Toast.makeText(this, "Permissions given", Toast.LENGTH_SHORT).show();
                        locationTracker=new LocationTracker(this);
                        if(locationTracker.canGetLocation()){
                            Log.d("Location", "latitude: "+locationTracker.getLatitude());
                            latitude = locationTracker.getLatitude();
                            longitude = locationTracker.getLongitude();
                            Log.d("Location", "latitude: "+locationTracker.getLongitude());
                        }

                        LatLng myLoc = new LatLng(latitude, longitude);
                        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(myLoc, 15f));
                        Marker marker = mMap.addMarker(new MarkerOptions()
                                .position(myLoc)
                                .title("Your Location")
                                .snippet("UnSafe")
                                .icon(BitmapDescriptorFactory
                                        .fromResource(R.drawable.my_loc)));

                        marker.showInfoWindow();
                        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(myLoc, 15f));
                        LatLng safeLoc = new LatLng(26.146818, 91.666981);
                        Marker safe = mMap.addMarker(new MarkerOptions()
                                .position(safeLoc)
                                .title("Safe Location")
                                .icon((BitmapDescriptorFactory.fromResource(R.drawable.safe_loc))));
                        safe.showInfoWindow();

                        String requestURL = getDirectionsUrl(myLoc,safeLoc);
                        TaskRequestDirection taskRequestDirections = new TaskRequestDirection();
                        taskRequestDirections.execute(requestURL);

                    }
                    else{
                        Toast.makeText(this, "Please grant the permissions", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private String requestDirection(String reqUrl){
        String response = "";
        InputStream inputStream = null;
        HttpURLConnection httpURLConnection = null;
        try{
            URL url = new URL(reqUrl);
            httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.connect();

            inputStream = httpURLConnection.getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

            StringBuffer stringBuffer = new StringBuffer();
            String line = "";
            while((line = bufferedReader.readLine()) != null){
                stringBuffer.append(line);
            }

            response = stringBuffer.toString();
            bufferedReader.close();
            inputStreamReader.close();

        }catch (Exception e){
            e.printStackTrace();
        }finally {
            httpURLConnection.disconnect();
        }
        return response;
    }

    public class TaskRequestDirection extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... strings) {
            String responseString = "";
            Log.i("debug", "response = null");
            try{
                responseString = requestDirection(strings[0]);
            }catch(Exception e){
                Log.i("debug","TaskReqDir" + e.toString());
                e.printStackTrace();
            }
            Log.i("debug", "response" + responseString);
            return responseString;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            TaskParser taskParser = new TaskParser();
            taskParser.execute(s);
        }
    }

    public class TaskParser extends AsyncTask<String, Void, List<List<HashMap<String, String>>>>{

        @Override
        protected List<List<HashMap<String, String>>> doInBackground(String... strings) {
            JSONObject jsonObject = null;
            List<List<HashMap<String, String>>> routes = null;
            try{
                jsonObject = new JSONObject(strings[0]);
                DirectionsParser directionsParser = new DirectionsParser();
                routes = directionsParser.parse(jsonObject);
            } catch(JSONException e){
                Log.i("debug", e.toString());
                e.printStackTrace();
            }
            return routes;
        }
        @Override
        protected void onPostExecute(List<List<HashMap<String, String>>> lists) {
            super.onPostExecute(lists);

            ArrayList points = null;
            PolylineOptions polylineOptions  = null;

            for (List<HashMap<String, String>> path : lists){
                points = new ArrayList();
                polylineOptions = new PolylineOptions();

                for (HashMap<String, String> point : path){
                    double lat = Double.parseDouble(point.get("lat"));
                    double lon = Double.parseDouble(point.get("lon"));

                    points.add(new LatLng(lat, lon));
                }

                polylineOptions.addAll(points);
                polylineOptions.width(15);
                polylineOptions.color(Color.BLUE);
                polylineOptions.geodesic(true);
            }
            if(polylineOptions != null){
                mMap.addPolyline(polylineOptions);
            } else {
                Toast.makeText(getApplicationContext(),"Unable Find Direction", Toast.LENGTH_SHORT).show();
            }

        }
    }

    private String getDirectionsUrl(LatLng origin, LatLng dest) {

        String str_origin = "origin=" + origin.latitude + "," + origin.longitude;
        String str_dest = "destination=" + dest.latitude + "," + dest.longitude;
        String sensor = "sensor=false";
        String mode = "mode=walking";
        String key = "&key=AIzaSyCmj-SVN9A37cmoih59waaX0Hxri6cWg90";
        String parameters = str_origin + "&" + str_dest + "&" + sensor + "&" + mode + key;
        String output = "json";
        String url = "https://maps.googleapis.com/maps/api/directions/" + output + "?" + parameters;
        Log.i("url", url);

        return url;
    }



    @SuppressLint("MissingPermission")
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case LOCATION_REQUEST:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    mMap.setMyLocationEnabled(true);
                }
                break;
        }
    }
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Bundle mapViewBundle = outState.getBundle(MAP_VIEW_BUNDLE_KEY);
        if(mapViewBundle == null){
            mapViewBundle = new Bundle();
            outState.putBundle(MAP_VIEW_BUNDLE_KEY, mapViewBundle);
        }
        mapView.onSaveInstanceState(mapViewBundle);
    }
    @Override
    public void onResume() {
        mapView.onResume();
        super.onResume();
    }
    @Override
    public void onPause() {
        mapView.onPause();
        super.onPause();
    }
    @Override
    public void onDestroy() {
        mapView.onDestroy();
        super.onDestroy();
    }
    @Override
    public void onLowMemory() {
        mapView.onLowMemory();
        super.onLowMemory();
    }
}


