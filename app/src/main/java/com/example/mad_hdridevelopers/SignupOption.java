package com.example.mad_hdridevelopers;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class SignupOption extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_option);

        getSupportActionBar().hide();
        Button wel2btn5=findViewById(R.id.wel2btn5);
        wel2btn5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeActivity();
            }
        });
    }
    private void changeActivity(){
        Intent business = new Intent(this,BusinessRegistration.class);
        startActivity(business);
    }
}