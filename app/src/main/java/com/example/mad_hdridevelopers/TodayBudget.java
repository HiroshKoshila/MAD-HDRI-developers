package com.example.mad_hdridevelopers;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class TodayBudget extends RecyclerView.Adapter<TodayBudget.viewHolder>{

    private Context bContext;
    private List<Budget> myBudgetList;
    private String postid;
    private String note;
    private int amount;
    private String item;

    public TodayBudget(Context bContext, List<Budget> myBudgetList) {
        this.bContext = bContext;
        this.myBudgetList = myBudgetList;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(bContext).inflate(R.layout.budget_output_layout,parent,false);
        return new TodayBudget.viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {

        final Budget budget = myBudgetList.get(position);
        holder.item.setText("Item: "+budget.getItem());
        holder.date.setText("Date: "+budget.getDate());
        holder.notes.setText("Notes: "+budget.getNotes());
        holder.amount.setText("Amount: "+budget.getAmount());

        switch (budget.getItem()){
            case "Food and Beverages":
                holder.imageView.setImageResource(R.drawable.budget_food);
                break;
            case "Transportation":
                holder.imageView.setImageResource(R.drawable.budget_transport);
                break;
            case "Vehicle":
                holder.imageView.setImageResource(R.drawable.budget_vehicle);
                break;
            case "Accomodation":
                holder.imageView.setImageResource(R.drawable.budget_accomodation);
                break;
            case "Others":
                holder.imageView.setImageResource(R.drawable.budget_other);
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + budget.getItem());
        }



    }

    @Override
    public int getItemCount() {
        return myBudgetList.size();

    }

    public class viewHolder extends RecyclerView.ViewHolder{
        public TextView item,amount,date ,notes;
        public ImageView imageView;

        public viewHolder(@NonNull View itemView) {
            super(itemView);

            item = itemView.findViewById(R.id.bItem);
            amount = itemView.findViewById(R.id.bAmount);
            notes = itemView.findViewById(R.id.bNote);
            date = itemView.findViewById(R.id.bDate);
            imageView = itemView.findViewById(R.id.budgetImageView);


        }
    }
}
