package com.example.choreapplication;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class DocumentedChoreAdapter extends RecyclerView.Adapter<DocumentedChoreViewHolder> {
    private ArrayList<DocumentedChore> documentedChores;

    public DocumentedChoreAdapter(ArrayList<DocumentedChore> documentedChores) {
        this.documentedChores = documentedChores;
    }

    @NonNull
    @Override
    public DocumentedChoreViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new DocumentedChoreViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.documented_chore_view_holder, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull DocumentedChoreViewHolder holder, int position) {
        DocumentedChore dc = documentedChores.get(position);
        holder.docUserTextView.setText(dc.getUser());
        holder.docChoreTextView.setText(dc.getChore());
        holder.docScoreTextView.setText(String.valueOf(dc.getScore()) + " p.");
    }

    @Override
    public int getItemCount() {
        return documentedChores.size();
    }
}
