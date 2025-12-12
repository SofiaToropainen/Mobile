package com.example.choreapplication;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class ChoreAdapter extends RecyclerView.Adapter<ChoreViewHolder> {
    private ArrayList<Chore> chores;

    public ChoreAdapter(ArrayList<Chore> chores) {
        this.chores = chores;
    }

    @NonNull
    @Override
    public ChoreViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ChoreViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.chore_view_holder, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ChoreViewHolder holder, int position) {
        Chore chore = chores.get(position);
        holder.choreHolderTextView.setText(chore.getChore());
        holder.scoreHolderTextView.setText(String.valueOf(chore.getScore() + " points"));
        holder.deleteButton.setOnClickListener(view -> {
            FirebaseFirestore.getInstance()
                    .collection("chores")
                    .document(chore.getId())
                    .delete()
                    .addOnSuccessListener(a -> {
                        chores.remove(position);
                        notifyItemRemoved(position);
                    });
        });
    }

    @Override
    public int getItemCount() {
        return chores.size();
    }
}
