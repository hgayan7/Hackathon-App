package com.adroitandroid.p2pchat;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.skyfishjy.library.RippleBackground;
import com.tbruyelle.rxpermissions2.RxPermissions;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    RippleBackground rippleBackground;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        RxPermissions rxPermissions = new RxPermissions(this);
        rxPermissions
                .request(Manifest.permission.CALL_PHONE,Manifest.permission.ACCESS_WIFI_STATE,Manifest.permission.CHANGE_NETWORK_STATE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_PHONE_STATE)
                .subscribe(granted -> {
                    if (granted) { // Always true pre-M
                        Toast.makeText(this, "Permissions given", Toast.LENGTH_SHORT).show();

                    } else {
                        Toast.makeText(this, "Please give the audio permission", Toast.LENGTH_SHORT).show();
                    }
                });

        rippleBackground= (RippleBackground) findViewById(R.id.ripple);
        rippleBackground.startRippleAnimation();
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
        } else if (id == R.id.nav_signin) {

        } else if (id == R.id.nav_emerin) {

        } else if (id == R.id.nav_p2p) {
            Intent intent=new Intent(MainActivity.this,P2PActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
