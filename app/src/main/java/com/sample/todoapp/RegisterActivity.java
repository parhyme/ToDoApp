package com.sample.todoapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.sample.todoapp.services.ApiService;

public class RegisterActivity extends AppCompatActivity {

    public static final String USERNAME = "username";
    public static final String PASSWORD = "password";
    EditText mTextUsername;
    EditText mTextPassword;
    EditText mTextCnfPassword;
    Button mButtonLogin;
    Button mButtonRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mTextUsername = findViewById(R.id.usernameid);
        mTextPassword = findViewById(R.id.passwordid);
        mTextCnfPassword = findViewById(R.id.cnfpassword);
        mButtonLogin = findViewById(R.id.login);
        mButtonRegister = findViewById(R.id.register);


        mButtonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent regIntent = new Intent(getBaseContext(), RegisterActivity.class);
                startActivity(regIntent);
            }
        });

        if (mTextCnfPassword.getText().toString().equals(mTextPassword.getText().toString())){
            mButtonRegister.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent serviceIntent = new Intent(getBaseContext(), ApiService.class);
                    serviceIntent.putExtra(USERNAME, mTextUsername.getText().toString());
                    serviceIntent.putExtra(PASSWORD, mTextPassword.getText().toString());

                    startService(serviceIntent);
                }
            });
        }


    }
}