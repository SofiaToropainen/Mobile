package com.example.choreapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.HashMap;

public class AddChoresActivity extends AppCompatActivity {
    private EditText choreToListEditText, scoreToListEditText;
    private Button addChoresToListButton, documentButton;
    private RecyclerView listChoresRV;
    private ArrayList<Chore> chores;
    private ChoreAdapter adapter;
    private FirebaseFirestore firestore;
    private String familyCode;
    private int scores;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add_chores);

        choreToListEditText = findViewById(R.id.choreToListEditText);
        scoreToListEditText = findViewById(R.id.scoreToListEditText);

        documentButton = findViewById(R.id.backToDocumentButton);

        listChoresRV = findViewById(R.id.listChoresRV);
        listChoresRV.setLayoutManager(new LinearLayoutManager(this));

        chores = new ArrayList<>();
        adapter = new ChoreAdapter(chores);

        listChoresRV.setAdapter(adapter);

        addChoresToListButton = findViewById(R.id.addChoresToListButton);
        addChoresToListButton.setOnClickListener(listener);

        firestore = FirebaseFirestore.getInstance();
        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();

        firestore.collection("users")
                .document(uid)
                .get()
                .addOnSuccessListener(userSnapshot -> {
                    familyCode = userSnapshot.getString("familyCode");
                    enableDocumentActivityButton();
                    listChores();
                });



        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            String chore = choreToListEditText.getText().toString();
            String score = scoreToListEditText.getText().toString();

            if(chore.isEmpty()) {
                Toast.makeText(AddChoresActivity.this, "Enter chore.",Toast.LENGTH_SHORT).show();
                return;
            }

            try {
                scores = Integer.parseInt(score);
            } catch (NumberFormatException e) {
                Toast.makeText(AddChoresActivity.this, "Please enter points as a whole number.", Toast.LENGTH_SHORT).show();
                return;
            }

            firestore.collection("chores")
                    .whereEqualTo("familyCode", familyCode)
                    .whereEqualTo("chore", chore)
                    .get()
                    .addOnSuccessListener(queryDocumentSnapshots -> {
                        if(!queryDocumentSnapshots.isEmpty()) {
                            Toast.makeText(AddChoresActivity.this, "The chore is already on the list.", Toast.LENGTH_SHORT).show();
                        } else {
                            HashMap<String, Object> choreData = new HashMap<>();
                            choreData.put("chore", chore);
                            choreData.put("score", scores);
                            choreData.put("familyCode", familyCode);

                            firestore.collection("chores")
                                    .add(choreData)
                                    .addOnSuccessListener(a -> {
                                        Toast.makeText(AddChoresActivity.this,"Chore added successfully.", Toast.LENGTH_SHORT).show();
                                        listChores();
                                        enableDocumentActivityButton();
                                    })
                                    .addOnFailureListener(a -> {
                                        Toast.makeText(AddChoresActivity.this,"Failed to add chore.", Toast.LENGTH_SHORT).show();
                                    });
                        }
                    });
            choreToListEditText.setText("");
            scoreToListEditText.setText("");
        }
    };

    public void listChores() {
        firestore.collection("chores")
                .whereEqualTo("familyCode", familyCode)
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    chores.clear();
                    for(QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                        Chore ch = documentSnapshot.toObject(Chore.class);
                        ch.setId(documentSnapshot.getId());
                        chores.add(ch);
                    }
                    adapter.notifyDataSetChanged();
                });
    }

    public void switchBackToDocumentActivity(View view) {
        Intent intent = new Intent(this, DocumentActivity.class);
        startActivity(intent);
    }

    public void enableDocumentActivityButton() {
        firestore.collection("chores")
                .whereEqualTo("familyCode", familyCode)
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    if(queryDocumentSnapshots.isEmpty()) {
                        documentButton.setEnabled(false);
                    } else {
                        documentButton.setEnabled(true);
                    }

                });
    }
}