package com.example.choreapplication;

// In SignUpActivity, the implementation is based on the Youtube video "Login and Signup Using Firebase Authentication in Android Studio | Java" by the Android Knowledge channel.
// https://www.youtube.com/watch?v=TStttJRAPhE

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class SignUpActivity extends AppCompatActivity {
    private TextInputEditText emailEditText, passwordEditText, usernameEditText, familyCodeEditText;
    private Button registerButton;
    private FirebaseAuth auth;
    private FirebaseFirestore firestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_sign_up);

        auth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();

        emailEditText = findViewById(R.id.emailEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        usernameEditText = findViewById(R.id.usernameEditText);
        familyCodeEditText = findViewById(R.id.familyCodeEditText);

        registerButton = findViewById(R.id.registerButton);
        registerButton.setOnClickListener(listener);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            String email = emailEditText.getText().toString();
            String password = passwordEditText.getText().toString();
            String username = usernameEditText.getText().toString();
            String familyCode = familyCodeEditText.getText().toString();

            if(!email.isEmpty() && !password.isEmpty() && !username.isEmpty() && !familyCode.isEmpty()) {
                auth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(authResult -> {

                            String uid = auth.getCurrentUser().getUid();

                            firestore.collection("families")
                                    .whereEqualTo("familyCode", familyCode)
                                    .get()
                                    .addOnSuccessListener(querySnapshot -> {
                                        if (!querySnapshot.isEmpty()) {
                                            DocumentSnapshot documentSnapshot = querySnapshot.getDocuments().get(0);
                                            String familyId = documentSnapshot.getId();

                                            firestore.collection("families")
                                                    .document(familyId)
                                                    .update("members", FieldValue.arrayUnion(uid))
                                                    .addOnSuccessListener(docRef -> {
                                                        saveUserAndContinue(uid, username, familyCode);
                                                    })
                                                    .addOnFailureListener(e -> {
                                                        Toast.makeText(SignUpActivity.this, "Failed to update family members.", Toast.LENGTH_SHORT).show();
                                                    });
                                        } else {
                                            HashMap<String, Object> family = new HashMap<>();
                                            family.put("familyCode", familyCode);

                                            List<String> members = new ArrayList<>();
                                            members.add(uid);
                                            family.put("members", members);

                                            firestore.collection("families")
                                                    .add(family)
                                                    .addOnSuccessListener(docRef -> {
                                                        saveUserAndContinue(uid, username, familyCode);
                                                    })
                                                    .addOnFailureListener(e -> {
                                                        Toast.makeText(SignUpActivity.this, "Could not create family.", Toast.LENGTH_SHORT).show();
                                                    });
                                        }
                                    });
                        });
            } else {
                Toast.makeText(SignUpActivity.this, "Please fill in all fields.", Toast.LENGTH_SHORT).show();
            }
        }
    };

    public void saveUserAndContinue(String uid, String username, String familyCode) {
        HashMap<String, Object> user = new HashMap<>();
        user.put("username", username);
        user.put("familyCode", familyCode);

        firestore.collection("users")
                .document(uid)
                .set(user)
                .addOnSuccessListener(aVoid -> {
                    Toast.makeText(SignUpActivity.this, "Sign up successful", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(SignUpActivity.this, LoginActivity.class));
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(SignUpActivity.this, "Could not save the user.", Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                });
    }


    public void moveToLoginActivity(View view) {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }
}