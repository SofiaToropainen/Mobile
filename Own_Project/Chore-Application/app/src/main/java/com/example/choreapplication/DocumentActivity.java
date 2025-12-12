package com.example.choreapplication;

// In the DocumentActivity, the spinner implementation is based on the Youtube video "How to Implement Spinner in Android" by the Codes Easy channel.
// https://www.youtube.com/watch?v=4ogzfAipGS8

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
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
import java.util.Date;

public class DocumentActivity extends AppCompatActivity {

    private Spinner spinner;
    private EditText timeEditText;
    private Button documentButton;
    private RecyclerView documentRV;
    private DocumentedChoreAdapter adapter;
    private ArrayList<Chore> chores;
    private ArrayList<DocumentedChore> documentedChores;
    private ArrayAdapter<String> spinnerAdapter;
    private String familyCode;
    private String username;
    private FirebaseAuth auth;
    private FirebaseFirestore firestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_document);

        spinner = findViewById(R.id.spinner);
        timeEditText = findViewById(R.id.timeEditText);

        chores = new ArrayList<>();
        documentedChores = new ArrayList<>();

        documentRV = findViewById(R.id.documentRV);
        documentRV.setLayoutManager(new LinearLayoutManager(this));
        adapter = new DocumentedChoreAdapter(documentedChores);
        documentRV.setAdapter(adapter);

        auth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();

        fetchFamilyCodeAndUsername();

        documentButton = findViewById(R.id.documentButton);
        documentButton.setOnClickListener(listener);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            String time = timeEditText.getText().toString();

            int minutes = 0;

            try {
                minutes = Integer.parseInt(time);
            } catch (NumberFormatException e) {
                Toast.makeText(DocumentActivity.this, "Please enter time in minutes.", Toast.LENGTH_SHORT).show();
                return;
            }

            if(minutes <= 0) {
                return;
            }

            String selectedItem = spinner.getSelectedItem().toString();

            Chore selectedChore = null;
            for(Chore c : chores) {
                if(c.getChore().equals(selectedItem)) {
                    selectedChore = c;
                    break;
                }
            }

            if(selectedChore == null) {
                Toast.makeText(DocumentActivity.this, "Please select a chore / add chores to the list first.", Toast.LENGTH_SHORT).show();
                return;
            }

            int score = selectedChore.getScore() + minutes;

            Date timestamp = new Date();

            DocumentedChore documentedChore = new DocumentedChore(selectedItem, familyCode, minutes, score, username, timestamp);

            firestore.collection("documentedChores")
                    .add(documentedChore)
                    .addOnSuccessListener(documentReference -> {
                        Toast.makeText(DocumentActivity.this,"Chore saved.",Toast.LENGTH_SHORT).show();
                        updateRecyclerView();
                        timeEditText.setText("");
                    })
                    .addOnFailureListener(e -> {
                        Toast.makeText(DocumentActivity.this, "Failed to save chore.", Toast.LENGTH_SHORT).show();
                    });
        }
    };

    public void fetchFamilyCodeAndUsername() {
        String uid = auth.getCurrentUser().getUid();

        firestore.collection("users")
                .document(uid)
                .get()
                .addOnSuccessListener(userSnapshot -> {
                    familyCode = userSnapshot.getString("familyCode");
                    username = userSnapshot.getString("username");
                    updateSpinner();
                    updateRecyclerView();
                });
    }

    public void fetchChores() {
        firestore.collection("chores")
                .whereEqualTo("familyCode", familyCode)
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    chores.clear();
                    spinnerAdapter.clear();
                    for(QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                        Chore chore = documentSnapshot.toObject(Chore.class);
                        chores.add(chore);
                        spinnerAdapter.add(chore.getChore());
                    }
                    spinnerAdapter.notifyDataSetChanged();
                });
    }

    public void updateSpinner() {
        spinnerAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item);
        spinnerAdapter.setDropDownViewResource(android.R.layout.select_dialog_singlechoice);
        spinner.setAdapter(spinnerAdapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String item = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        fetchChores();
    }

    public void updateRecyclerView() {
        firestore.collection("documentedChores")
                .whereEqualTo("familyCode", familyCode)
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    documentedChores.clear();
                    for(QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                        DocumentedChore dc = documentSnapshot.toObject(DocumentedChore.class);
                        documentedChores.add(dc);
                    }
                    adapter.notifyDataSetChanged();
                });
    }

    public void switchToAddChoresActivity(View view) {
        Intent intent = new Intent(this, AddChoresActivity.class);
        startActivity(intent);
    }

    public void switchBackToFirstPageActivity(View view) {
        Intent intent = new Intent(this, FirstPageActivity.class);
        startActivity(intent);
    }
}