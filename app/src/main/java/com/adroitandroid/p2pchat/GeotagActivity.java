package com.adroitandroid.p2pchat;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.ExifInterface;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

public class GeotagActivity extends AppCompatActivity {

    LocationTracker locationTracker;
    Uri uri;
    ImageView imageView;
    private byte[] byteArray;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_geotag);
        locationTracker=new LocationTracker(this);
        imageView=(ImageView) findViewById(R.id.cameraOutput);

    }

    public void captureImage(View view){
        setImagePath();
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
        startActivityForResult(intent, 100);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==100){
            Log.d("Result", "onActivityResult: "+requestCode);
            String[] projection = {MediaStore.Images.Media.DATA};
            Cursor cursor = managedQuery(uri, projection, null, null, null);
            int column_index_data = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            String capturedImageFilePath = cursor.getString(column_index_data);
            Uri imageuri = Uri.parse("file:///" + capturedImageFilePath);
            setGeoTag(capturedImageFilePath);
            imageView.setImageBitmap(compressImage(imageuri));

        }
    }

    private Bitmap compressImage(Uri imageU){
        InputStream inputStream=null;
        try{
            inputStream=getContentResolver().openInputStream(imageU);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        final BitmapFactory.Options options=new BitmapFactory.Options();
        options.inJustDecodeBounds=true;
        Bitmap bitmap=BitmapFactory.decodeStream(inputStream);
        ByteArrayOutputStream byteArrayOutputStream=new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,20,byteArrayOutputStream);
        byteArray = byteArrayOutputStream.toByteArray();
        try {
            byteArrayOutputStream.close();
            byteArrayOutputStream = null;
        } catch (IOException e) {

            e.printStackTrace();
        }
        return bitmap;
    }


    public void setGeoTag(String filepath){
        ExifInterface exifInterface= null;
        try {
            exifInterface = new ExifInterface(filepath);
        } catch (IOException e) {
            e.printStackTrace();
        }
        exifInterface.setAttribute(ExifInterface.TAG_GPS_LATITUDE,String.valueOf(locationTracker.getLatitude()));
        exifInterface.setAttribute(ExifInterface.TAG_GPS_LONGITUDE,String.valueOf(locationTracker.getLongitude()));
        Log.d("Result", "setGeoTag: "+locationTracker.getLatitude());
        Log.d("Result", "setGeoTag: "+locationTracker.getLongitude());


        try {
            exifInterface.saveAttributes();
            Log.d("Result", "setGeoTag: Metadata saved");
        } catch (IOException e) {
            e.printStackTrace();
        }
        Log.d("Result", "setGeoTag: success");
    }
    private void setImagePath() {

        ContentValues values = new ContentValues();
        values.put(MediaStore.Images.Media.TITLE, System.currentTimeMillis() + ".png");
        values.put(MediaStore.Images.Media.TITLE, System.currentTimeMillis() + ".jpg");
        uri = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);

    }


}
