package com.example.choreapplication.fragments;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.choreapplication.R;

import java.util.ArrayList;

public class StatisticsAdapter extends RecyclerView.Adapter<StatisticsViewHolder> {
    private ArrayList<Statistics> statistics;

    public StatisticsAdapter(ArrayList<Statistics> statistics) {
        this.statistics = statistics;
    }

    @NonNull
    @Override
    public StatisticsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new StatisticsViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.statistics_view_holder, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull StatisticsViewHolder holder, int position) {
        Statistics information = statistics.get(position);
        holder.usernameHolderTV.setText(String.valueOf(information.getUser()));
        holder.scoresHolderTV.setText(String.valueOf(information.getTotalScore()) + " points");
        holder.hoursHolderTV.setText(String.valueOf(information.getTotalTime()));
    }

    @Override
    public int getItemCount() {
        return statistics.size();
    }
}
