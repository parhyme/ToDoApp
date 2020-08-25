package com.sample.todoapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.sample.todoapp.models.User;
import com.sample.todoapp.services.ApiService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SignInActivity extends AppCompatActivity {

    public static final String USERNAME = "username";
    public static final String PASSWORD = "password";
    EditText mTextUsername;
    EditText mTextPassword;
    Button mButtonLogin;
    Button mButtonRegister;

    List<User> users = new ArrayList<>();

//    Gson gson= new Gson();
    JsonPlaceHolderApi jsonPlaceHolderApi;
    ApiService apiService;
    Context context;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);


        context = this;
        mTextUsername = findViewById(R.id.username);
        mTextPassword = findViewById(R.id.password);
        mButtonLogin = findViewById(R.id.login);
        mButtonRegister = findViewById(R.id.registerid);

        ServiceConnection sc = new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
                ApiService.mBinder binder = (ApiService.mBinder) iBinder;
                apiService = binder.getApiService();
                if(apiService.isLoginVerification()){
                    Toast.makeText(getBaseContext(), "VERIFIED", Toast.LENGTH_LONG).show();
                }else {
                    Toast.makeText(getBaseContext(), "NOT VERIFIED", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onServiceDisconnected(ComponentName componentName) {

            }
        };



        mButtonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent serviceIntent = new Intent(getBaseContext(), ApiService.class);
                serviceIntent.putExtra(USERNAME, mTextUsername.getText().toString());
                serviceIntent.putExtra(PASSWORD, mTextPassword.getText().toString());
//                serviceIntent.putExtra(ApiService.CONTEXT,context.);
                serviceIntent.putExtra(ApiService.TASK_STRING, "login");
                startService(serviceIntent);
            }
        });

        mButtonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent regIntent = new Intent(SignInActivity.this,RegisterActivity.class);
                startActivity(regIntent);
            }
        });



    }
}