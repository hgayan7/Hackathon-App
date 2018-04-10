package com.adroitandroid.p2pchat;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.IInterface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Arrays;

import Model.HealthProblem;
import Model.OriginLoc;
import Model.SignUp;
import Rest.ApiClient;
import Rest.ApiInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    ApiInterface apiInterface;
    private EditText et_gen_id, et_username, et_password, et_email, et_full_name, et_age,et_health,pincodeeditext;
    private String gen_id, username, password, email, full_name, age, blood_group,healthProblem,pincode;
    private Spinner mySpinner;
    Button regbtn;
    int spinnerId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        et_gen_id = (EditText) findViewById(R.id.gen_id);
        et_username = (EditText) findViewById(R.id.username);
        et_password = (EditText) findViewById(R.id.password);
        et_email = (EditText) findViewById(R.id.email);
        et_full_name = (EditText) findViewById(R.id.full_name);
        et_age = (EditText) findViewById(R.id.age);
        mySpinner = (Spinner) findViewById(R.id.blood_group);
        regbtn = (Button) findViewById(R.id.reg_button);
        pincodeeditext=(EditText)findViewById(R.id.pincode);

        ArrayAdapter<String> myadapter = new ArrayAdapter<String>(RegisterActivity.this,
                android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.blood_group));

        myadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mySpinner.setAdapter(myadapter);
        apiInterface= ApiClient.getClient().create(ApiInterface.class);
        sharedPreferences=getSharedPreferences("Database", Context.MODE_PRIVATE);
        editor=sharedPreferences.edit();
        mySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                spinnerId=i;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        regbtn= (Button) findViewById(R.id.reg_button);
        regbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callRegister();
            }
        });
    }

    public String checkSpinnerValue(int value){

        if(value==1){
            return "O+";
        }else if(value==2){
            return "O-";
        }else if(value==3){
            return "A+";
        }else if(value==4){
            return "A-";
        }else if(value==5){
            return "B+";
        }else if(value==6){
            return "B-";
        }else if(value==7){
            return "AB+";
        }else if(value==8){
            return "AB-";
        }else {
            return "NA";
        }
    }

    public void callRegister(){
        gen_id = et_gen_id.getText().toString().trim();
        username = et_username.getText().toString().trim();
        password = et_password.getText().toString().trim();
        email = et_email.getText().toString().trim();
        full_name = et_full_name.getText().toString().trim();
        age = et_age.getText().toString().trim();
        pincode=pincodeeditext.getText().toString().trim();

        // OriginLoc originLoc = new OriginLoc(12.31f, 41.1f);
        //    HealthProblem healthProblem = new HealthProblem(et_health.getText().toString());
            HealthProblem healthProblem1 = new HealthProblem("cancer");
            SignUp signUp = new SignUp(gen_id, username,full_name,password,email,Integer.valueOf(et_age.getText().toString()),"dsds"
                    , new ArrayList<HealthProblem>(Arrays.asList(healthProblem1)),Integer.valueOf(pincodeeditext.getText().toString()));
            Gson gson = new Gson();
            String registerData = gson.toJson(signUp);
            Log.d("Json", "onCreate: " + registerData.toString());
            Call<SignUp> responseBodyCall;
            responseBodyCall = apiInterface.postSignUp(signUp);
            responseBodyCall.enqueue(new Callback<SignUp>() {
                @Override
                public void onResponse(Call<SignUp> call, Response<SignUp> response) {
                    Toast.makeText(RegisterActivity.this, "Code:" + response.code() + response.body() + response.errorBody(), Toast.LENGTH_SHORT).show();
                    Log.d("Response", "onResponse: " + response.code() + response.body().getToken());
                    editor.putString("tokenId",response.body().getToken());
                }

                @Override
                public void onFailure(Call<SignUp> call, Throwable t) {
                    Log.d("Error", "onFailure: " + t.getMessage());
                }
            });

    }



    public boolean validate() {
        boolean valid = true;
        if(et_gen_id.getText().toString().isEmpty()|| et_gen_id.length() != 6) {
            et_gen_id.setError("Enter a valid ID (ID must contain 6 characters)");
            valid = false;
        }
        if(et_username.getText().toString().isEmpty() || username.length() > 32) {
            et_username.setError("Enter a valid Username");
            valid = false;
        }
        if(et_password.getText().toString().isEmpty()) {
            et_password.setError("Enter a password");
            valid = false;
        }
        if(et_email.getText().toString().isEmpty()|| !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            et_email.setError("Enter a valid E-mail Address");
            valid = false;
        }
        if(et_full_name.getText().toString().isEmpty()) {
            et_full_name.setError("Enter a valid Name");
            valid = false;
        }
        if(et_age.getText().toString().isEmpty()) {
            et_age.setError("Enter a valid age");
            valid = false;
        }
        return valid;
    }



}

