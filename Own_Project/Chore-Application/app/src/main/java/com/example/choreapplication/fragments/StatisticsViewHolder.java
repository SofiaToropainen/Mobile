package com.example.choreapplication.fragments;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.choreapplication.R;

public class StatisticsViewHolder extends RecyclerView.ViewHolder {
    TextView usernameHolderTV, scoresHolderTV, hoursHolderTV;

    public StatisticsViewHolder(@NonNull View itemView) {
        super(itemView);
        usernameHolderTV = itemView.findViewById(R.id.userHolderTextView);
        scoresHolderTV = itemView.findViewById(R.id.scoresHolderTextView);
        hoursHolderTV = itemView.findViewById(R.id.hoursHolderTextView);
    }
}
