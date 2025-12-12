package com.example.choreapplication;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ChoreViewHolder extends RecyclerView.ViewHolder {
    TextView choreHolderTextView, scoreHolderTextView;
    ImageView deleteButton;

    public ChoreViewHolder(@NonNull View itemView) {
        super(itemView);
        choreHolderTextView = itemView.findViewById(R.id.choreHolderTextView);
        scoreHolderTextView = itemView.findViewById(R.id.scoreHolderTextView);
        deleteButton = itemView.findViewById(R.id.deleteButton);
    }
}
