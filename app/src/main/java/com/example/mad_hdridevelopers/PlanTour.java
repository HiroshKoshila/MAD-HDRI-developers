package com.example.mad_hdridevelopers;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.Toolbar;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.jetbrains.annotations.NotNull;

public class PlanTour extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {


    private RecyclerView recyclerView;
    private FloatingActionButton floatingActionButton;

    private DatabaseReference reference;
    private FirebaseAuth mAuth;
    private FirebaseUser mUser;
    private String onlineuserID ;

    private ProgressDialog loader;

    private String key = "";
    private String task;
    private String description;
    private String time;

    Button cancel;

    //side Nav
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    androidx.appcompat.widget.Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plan_tour);



        //toolbar
        toolbar =findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Tour Planner");

        recyclerView = findViewById(R.id.planrecyclerview);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(linearLayoutManager);

        //loader = new ProgressDialog(this);

        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();
        onlineuserID = mUser.getUid();
        reference = FirebaseDatabase.getInstance().getReference().child("tasks").child(onlineuserID);


        floatingActionButton = findViewById(R.id.fab);
        floatingActionButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                addTask();
            }
        });

        //side nav

        drawerLayout = findViewById(R.id.plan_layout);
        navigationView = findViewById(R.id.nav_view);

        //navigate menu side

        navigationView.bringToFront();
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.navigation_drawer_open,R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        //click on nav side
        navigationView.setNavigationItemSelectedListener(this);

        navigationView.setCheckedItem(R.id.nav_trans);





    }

    private void addTask() {
        AlertDialog.Builder myDialog = new AlertDialog.Builder(this);
        LayoutInflater inflator = LayoutInflater.from(this);

        View myView = inflator.inflate(R.layout.planinput_file, null);
        myDialog.setView(myView);

        AlertDialog dialog = myDialog.create();
        dialog.setCancelable(false);
        dialog.show();

        final EditText task = myView.findViewById(R.id.et_plantask);
        final EditText description = myView.findViewById(R.id.et_plandesc);
        final EditText time = myView.findViewById(R.id.et_plantime);
        Button save = myView.findViewById(R.id.planbtn_save);
        Button cancel = myView.findViewById(R.id.planbtn_cancel);


        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mapView = new Intent(PlanTour.this, PlanTour.class);
                startActivity(mapView);
            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mapView = new Intent(PlanTour.this, PlanTour.class);
                startActivity(mapView);
            }
        });
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull @NotNull MenuItem item) {

        switch (item.getItemId()){
            case R.id.nav_home:
                Intent intent3 = new Intent(PlanTour.this,Home.class);
                startActivity(intent3);
                break;
            case R.id.nav_trans:
                break;
            case R.id.nav_cal:
                Intent intent2 = new Intent(PlanTour.this,BudgetCal1.class);
                startActivity(intent2);
                Toast.makeText(this,"Cal",Toast.LENGTH_SHORT).show();
                break;
            case R.id.nav_user:
                Intent intent4 = new Intent(PlanTour.this,UserProfile.class);
                startActivity(intent4);
                break;
            case R.id.nav_logout:
                Intent signOut = new Intent(PlanTour.this,LoginPage.class);
                startActivity(signOut);
                break;
        }

        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackPressed() {
        if(drawerLayout.isDrawerOpen((GravityCompat.START))){
            drawerLayout.closeDrawer(GravityCompat.START);
        }
        else{
            super.onBackPressed();
        }
    }
}