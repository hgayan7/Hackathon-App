package com.adroitandroid.p2pchat;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;

import Model.SignUp;
import Rest.ApiClient;
import Rest.ApiInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddFriendActivity extends AppCompatActivity {

    EditText addFriend;
    ApiInterface apiInterface;
    Call<SignUp> call;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_friend);
        sharedPreferences=getSharedPreferences("Database", Context.MODE_PRIVATE);
        editor=sharedPreferences.edit();
        addFriend=(EditText)findViewById(R.id.addFriendId);
        apiInterface= ApiClient.getClient().create(ApiInterface.class);

    }
    public void addFriend(View view){
        SignUp signUp=new SignUp(sharedPreferences.getString("genId","na"));
        String s=sharedPreferences.getString("id","na");
        Log.d("Result", "addFriend: "+sharedPreferences.getString("tokenId","NA"));
        Gson gson=new Gson();
        String a=sharedPreferences.getString("id","na");
        call=apiInterface.putFriend("Bearer "+s,signUp,a);

        call.enqueue(new Callback<SignUp>() {
            @Override
            public void onResponse(Call<SignUp> call, Response<SignUp> response) {

                Toast.makeText(AddFriendActivity.this, "Friend added"+response.code(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<SignUp> call, Throwable t) {
                Toast.makeText(AddFriendActivity.this, "Adding friend failed", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
