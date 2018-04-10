package com.adroitandroid.p2pchat;

import android.content.Intent;
import android.net.Uri;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class EmergencyActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emergency);


    }
    public void one(View view){
        startActivity(new Intent(Intent.ACTION_DIAL, Uri.parse("tel:2514")));
    }
    public void two(View view){
        startActivity(new Intent(Intent.ACTION_DIAL, Uri.parse("tel:1154")));
    }
    public void three(View view){
        startActivity(new Intent(Intent.ACTION_DIAL, Uri.parse("tel:74687")));
    }
    public void four(View view){
        startActivity(new Intent(Intent.ACTION_DIAL, Uri.parse("tel:6468")));
    }
}
