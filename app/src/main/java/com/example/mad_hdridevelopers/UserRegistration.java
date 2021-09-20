package com.example.mad_hdridevelopers;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class UserRegistration extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_registration);

        getSupportActionBar().setTitle("User Registration");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }
}