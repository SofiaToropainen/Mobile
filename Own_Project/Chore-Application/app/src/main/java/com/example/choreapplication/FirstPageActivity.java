package com.example.choreapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class FirstPageActivity extends AppCompatActivity {

    private Button documentActivityButton, signOutActivityButton;
    private FirebaseAuth auth;
    private FirebaseFirestore firestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_first_page);

        auth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();

        signOutActivityButton = findViewById(R.id.signOutActivityButton);
        signOutActivityButton.setOnClickListener(listener1);

        documentActivityButton = findViewById(R.id.documentActivityButton);
        documentActivityButton.setOnClickListener(listener2);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private View.OnClickListener listener1 = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            auth.signOut();
            Intent intent = new Intent(FirstPageActivity.this, MainActivity.class);
            startActivity(intent);
        }
    };

    private View.OnClickListener listener2 = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            String uid = auth.getCurrentUser().getUid();

            firestore.collection("users")
                    .document(uid)
                    .get()
                    .addOnSuccessListener(userSnapshot -> {
                        String familyCode = userSnapshot.getString("familyCode");

                        firestore.collection("chores")
                                .whereEqualTo("familyCode", familyCode)
                                .get()
                                .addOnSuccessListener(queryDocumentSnapshots -> {
                                    if (!queryDocumentSnapshots.isEmpty()) {
                                        Intent intent = new Intent(FirstPageActivity.this, DocumentActivity.class);
                                        startActivity(intent);
                                    } else {
                                        Intent intent = new Intent(FirstPageActivity.this, AddChoresActivity.class);
                                        startActivity(intent);
                                    }
                                });
                    });
        }
    };

    public void switchToStatisticsActivity(View view) {
        Intent intent = new Intent(this, StatisticsActivity.class);
        startActivity(intent);
    }
}