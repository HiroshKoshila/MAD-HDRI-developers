package com.example.mad_hdridevelopers;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

public class BudgetCal extends AppCompatActivity {

    TextView TotalAmountTV;
    RecyclerView recyclerView;
    FloatingActionButton fab;

    private FirebaseAuth mAuth;
    private DatabaseReference ref;
    private String onlineUserId = "";
    private ProgressDialog loader;

    private TodayBudget todayBudget;
    private List<Budget> myBudgetList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_budget_cal);

        TotalAmountTV = findViewById(R.id.TotalAmount);

        fab = findViewById(R.id.fab);

        mAuth = FirebaseAuth.getInstance();
        onlineUserId = mAuth.getCurrentUser().getUid();
        ref = FirebaseDatabase.getInstance().getReference().child("Budget").child(onlineUserId);
        loader = new ProgressDialog(this);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addItemSpentOn();
            }
        });

        recyclerView = findViewById(R.id.recyclerView);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(linearLayoutManager);

        myBudgetList = new ArrayList<>();
        todayBudget = new TodayBudget(BudgetCal.this,myBudgetList);
        recyclerView.setAdapter(todayBudget);
        readBudget();

    }

    private void readBudget(){
        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        Calendar cal = Calendar.getInstance();
        String date = dateFormat.format(cal.getTime());

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Budget").child(onlineUserId);
        Query query = reference.orderByChild("date").equalTo(date);
        query.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                myBudgetList.clear();
                for (DataSnapshot dataSnapshot: snapshot.getChildren()){
                    Budget budget = snapshot.getValue(Budget.class);
                    myBudgetList.add(budget);
                }

                todayBudget.notifyDataSetChanged();

                int totalAmount;
                totalAmount = 0;

                for(DataSnapshot ds: snapshot.getChildren()){
                    Map<String,Object> map = (Map <String, Object>) ds.getValue();
                    Object total = map.get("amount");
                    int pTotal = Integer.parseInt(String.valueOf(total));
                    totalAmount += pTotal;

                    TotalAmountTV.setText("Total Day Spending: Rs."+ totalAmount);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    private void addItemSpentOn() {

        AlertDialog.Builder myDialog = new AlertDialog.Builder(this);
        LayoutInflater inflater = LayoutInflater.from(this);

        View myView = inflater.inflate(R.layout.budget_input_layout, null);
        myDialog.setView(myView);

        final AlertDialog dialog = myDialog.create();
        myDialog.setCancelable(false);

        final Spinner itemsSpinner = myView.findViewById(R.id.spinner);
        ArrayAdapter<String> itemsAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.items));
        itemsAdapter.setDropDownViewResource(android.R.layout.simple_list_item_1);
        itemsSpinner.setAdapter(itemsAdapter);

        final EditText amount = myView.findViewById(R.id.b_amount);
        final EditText note = myView.findViewById(R.id.b_note);

        final Button cancel = myView.findViewById(R.id.b_cancel);
        final Button save = myView.findViewById(R.id.b_save);

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String mAmount = amount.getText().toString();
                String mNote = note.getText().toString();
                String item = itemsSpinner.getSelectedItem().toString();

                if(mAmount.isEmpty()){
                    amount.setError("Amount is Required");
                    return;
                }
                if(mNote.isEmpty()){
                    note.setError("Note is Required");
                    return;
                }
                if(item.equals("Select Item")){
                    Toast.makeText(BudgetCal.this, "Select a Valid Item",Toast.LENGTH_SHORT).show();
                }
                else{
                    loader.setMessage("Adding Item to Database");
                    loader.setCanceledOnTouchOutside(false);
                    loader.show();

                    String id = ref.push().getKey();
                    DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
                    Calendar cal = Calendar.getInstance();
                    String date = dateFormat.format(cal.getTime());

                    Budget budget = new Budget(item, date, id, mNote,Integer.parseInt(mAmount));
                    ref.child(id).setValue(budget).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){
                                Toast.makeText(BudgetCal.this, "Item Added Successfully", Toast.LENGTH_SHORT).show();
                            }else{
                                Toast.makeText(BudgetCal.this, "Failed", Toast.LENGTH_SHORT).show();
                            }
                            loader.dismiss();
                        }
                    });
                }
                dialog.dismiss();

            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        dialog.show();

    }




}