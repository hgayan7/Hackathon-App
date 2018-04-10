package com.adroitandroid.p2pchat;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import Model.SignIn;
import Rest.ApiClient;
import Rest.ApiInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignInActivity extends AppCompatActivity {
    ApiInterface apiInterface;
    EditText username,password;
    Call<SignIn> call;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        username=(EditText) findViewById(R.id.usernameSignIn);
        password=(EditText)findViewById(R.id.passwordSignIn);
        apiInterface= ApiClient.getClient().create(ApiInterface.class);
        sharedPreferences=getSharedPreferences("Database", Context.MODE_PRIVATE);
        editor=sharedPreferences.edit();
    }
    public void signIn(View view){
        if(validate())
            call=apiInterface.postSignIn(username.getText().toString(),password.getText().toString());
        call.enqueue(new Callback<SignIn>() {
            @Override
            public void onResponse(Call<SignIn> call, Response<SignIn> response) {
                Toast.makeText(SignInActivity.this, "Code:"+response.code(), Toast.LENGTH_SHORT).show();
                editor.putString("genId",response.body().getGenId());
                editor.putString("tokenId",response.body().getToken());
                editor.putString("id",response.body().getId());
                editor.commit();

            }

            @Override
            public void onFailure(Call<SignIn> call, Throwable t) {
                Toast.makeText(SignInActivity.this, "Please try again", Toast.LENGTH_SHORT).show();
            }
        });
    }
    public boolean validate(){

        if(username.getText().toString().isEmpty()){
            Toast.makeText(this, "Enter username", Toast.LENGTH_SHORT).show();
            return false;
        }
        else if(password.getText().toString().isEmpty()){
            Toast.makeText(this, "Enter password", Toast.LENGTH_SHORT).show();
            return false;
        }
        else {
            return true;
        }
    }
}
