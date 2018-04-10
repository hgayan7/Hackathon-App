package com.adroitandroid.p2pchat;

import android.content.Context;
import android.content.SharedPreferences;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.util.ArrayList;
import java.util.List;


public class EarthquakeActivity extends AppCompatActivity implements SensorEventListener {

    private final Handler mHandler = new Handler();
    private Runnable mTimer1;
    private LineGraphSeries<DataPoint> mSeries1;
    private LineGraphSeries<DataPoint> mSeries2;
    DataPoint[] dangerDataPoints;
    List<DataPoint> dataList,earthquakeDataList;

    float x,y;
    private SensorManager senSensorManager;
    private Sensor senAccelerometer;
    GraphView graphView,graphView2;

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    int i;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_earthquake);
        sharedPreferences=getSharedPreferences("Database", Context.MODE_PRIVATE);
        editor=sharedPreferences.edit();
        //   RxPermissions rxPermissions=new RxPermissions(this);
        dataList=new ArrayList<>();
        earthquakeDataList=new ArrayList<>();

        earthquakeDataList.add(new DataPoint(0.03,4.2));
        earthquakeDataList.add(new DataPoint(.603,5.1));
        earthquakeDataList.add(new DataPoint(.703,5.7));
        earthquakeDataList.add(new DataPoint(8.53,6.01));
        earthquakeDataList.add(new DataPoint(8.03,7.1));


        mSeries1 = new LineGraphSeries<>();
        graphView=(GraphView)findViewById(R.id.graph);
        graphView2=(GraphView) findViewById(R.id.graph2);
        senSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        senAccelerometer = senSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        senSensorManager.registerListener(this, senAccelerometer , SensorManager.SENSOR_DELAY_NORMAL);

        mSeries2=new LineGraphSeries<>(new DataPoint[]{
                new DataPoint(0.03,4.2),
                new DataPoint(.603,5.1),
                new DataPoint(.703,5.7),
                new DataPoint(8.53,6.01),
                new DataPoint(8.03,7.1),

        });
        graphView2.addSeries(mSeries2);
        graphView2.getViewport().setXAxisBoundsManual(true);
        graphView.getViewport().setMinX(0);
        graphView.getViewport().setMaxX(40);

        graphView.addSeries(mSeries1);
        graphView.getViewport().setXAxisBoundsManual(true);
        graphView.getViewport().setMinX(0);
        graphView.getViewport().setMaxX(40);
    }


    @Override
    public void onSensorChanged(SensorEvent event) {
        Sensor mySensor = event.sensor;


        if (mySensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            x = event.values[0];
            y = event.values[1];
            //  float z = event.values[2];
            //  editor.putString("x",String.valueOf(x));
            //  editor.putString("y",String.valueOf(y));
        }
        Log.d("Sensor", "onSensorChanged: "+"Sensor chanee called");
        mTimer1 = new Runnable() {
            @Override
            public void run() {
                i=i+10;
                mSeries1.appendData(new DataPoint(x+i,y), true, 100);
                mHandler.postDelayed(this, 1000);
                Log.d("Sensor", "run: "+"Run called");
                dataList.add(new DataPoint(x,y));


            }
        };
        mHandler.postDelayed(mTimer1, 400);
        Log.d("Sensor", "onSensorChanged: "+"x is "+x+" y is " +y);

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    @Override
    protected void onResume() {
        super.onResume();

        senSensorManager.registerListener(this, senAccelerometer, SensorManager.SENSOR_DELAY_NORMAL);
      /*  mTimer1 = new Runnable() {
            @Override
            public void run() {
                mSeries1.resetData(dataPoints);
                mHandler.postDelayed(this, 300);
            }
        };
        mHandler.postDelayed(mTimer1, 300);
        */
        mTimer1 = new Runnable() {
            @Override
            public void run() {
                i=i+10;
                mSeries1.appendData(new DataPoint(x+i, y), true, 40);
                mHandler.postDelayed(this, 4000);
            }
        };
        mHandler.postDelayed(mTimer1, 1000);

    }

    @Override
    public void onPause() {
        mHandler.removeCallbacks(mTimer1);
        senSensorManager.unregisterListener(this);
        super.onPause();
        Log.d("Sensor", "onPause: Called");
    }
}

