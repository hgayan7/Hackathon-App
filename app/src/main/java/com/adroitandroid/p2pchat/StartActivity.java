package com.adroitandroid.p2pchat;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.adroitandroid.p2pchat.ChatBot.ChatbotMainScreen;
import com.adroitandroid.p2pchat.MapsPackage.MapsMain;
import com.adroitandroid.p2pchat.NewsPackage.NewsScreen;
import com.skyfishjy.library.RippleBackground;
import com.tbruyelle.rxpermissions2.RxPermissions;

import Model.SignIn;

public class StartActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    RippleBackground rippleBackground;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        rippleBackground=(RippleBackground) findViewById(R.id.content);
        rippleBackground.startRippleAnimation();
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        RxPermissions rxPermissions=new RxPermissions(this);
        rxPermissions.request(Manifest.permission.ACCESS_COARSE_LOCATION,Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_NETWORK_STATE,Manifest.permission.ACCESS_WIFI_STATE,Manifest.permission.CALL_PHONE,
                Manifest.permission.CAMERA,Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .subscribe(granted-> {
                    if(granted){
                        Toast.makeText(this, "Granted", Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(this, "Not granted", Toast.LENGTH_SHORT).show();
                    }

                });



    }

    public void emergency(View view){
        Intent intent=new Intent(StartActivity.this,EmergencyActivity.class);
        startActivity(intent);
    }
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_register) {
            // Handle the camera action

            Intent intent=new Intent(StartActivity.this,RegisterActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_signin) {
            Intent intent=new Intent(StartActivity.this, SignInActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_offlinechat) {

            Intent intent=new Intent(StartActivity.this,MainActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_earthquake) {
            Intent intent=new Intent(StartActivity.this,EarthquakeActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_geotag) {
            Intent intent=new Intent(StartActivity.this,GeotagActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_addfriend) {
            Intent intent=new Intent(StartActivity.this,AddFriendActivity.class);
            startActivity(intent);
        }else if(id==R.id.nav_map){
            Intent intent=new Intent(StartActivity.this, MapsMain.class);
            startActivity(intent);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void goToChatbot(View view){
        Intent intent=new Intent(StartActivity.this, ChatbotMainScreen.class);
        startActivity(intent);
    }
    public void goToNews(View view){
        Intent intent=new Intent(StartActivity.this, NewsScreen.class);
        startActivity(intent);
    }
}
