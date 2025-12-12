package com.example.choreapplication;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class DocumentedChoreViewHolder extends RecyclerView.ViewHolder {
    TextView docUserTextView, docChoreTextView, docScoreTextView;

    public DocumentedChoreViewHolder(@NonNull View itemView) {
        super(itemView);
        docUserTextView = itemView.findViewById(R.id.docUserTextView);
        docChoreTextView = itemView.findViewById(R.id.docChoreTextView);
        docScoreTextView = itemView.findViewById(R.id.docScoreTextView);
    }
}

