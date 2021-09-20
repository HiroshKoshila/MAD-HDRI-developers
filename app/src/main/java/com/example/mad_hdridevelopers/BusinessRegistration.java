package com.example.mad_hdridevelopers;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class BusinessRegistration extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_business_registration);
        getSupportActionBar().hide();
        Button button4=findViewById(R.id.button4);
        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeActivity();
            }
        });
    }
    private void changeActivity(){
        Intent transreg = new Intent(this,TransportProviderReg.class);
        startActivity(transreg);
    }
}