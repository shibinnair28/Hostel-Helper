package com.example.hostelhelper;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class CreateAccount extends AppCompatActivity {

    private EditText nameEditText, emailEditText, passwordEditText, repasswordEditText, roomno, sapid;
    private Button registerButton;

    private FirebaseAuth mAuth;
    FirebaseFirestore fStore;
    String UserID;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);

        mAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        nameEditText = findViewById(R.id.name);
        emailEditText = findViewById(R.id.email);
        passwordEditText = findViewById(R.id.password);
        repasswordEditText = findViewById(R.id.repassword);
        registerButton = findViewById(R.id.signupbtn);
        roomno = findViewById(R.id.room);
        sapid = findViewById(R.id.sapid);

        // Adding a TextWatcher for real-time password validation
        passwordEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!isValidPassword(s.toString())) {
                    passwordEditText.setError("Password is weak. Use at least 8 characters including uppercase, lowercase, digits, and special characters.");
                }
            }
        });

        // Adding a TextWatcher for the retype password field
        repasswordEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                String password = passwordEditText.getText().toString().trim();
                String repassword = s.toString().trim();

                if (!repassword.equals(password)) {
                    repasswordEditText.setError("Passwords do not match");
                } else {
                    repasswordEditText.setError(null); // Clear error
                }
            }
        });

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createAccount();
            }
        });
    }

    private boolean isValidPassword(String password) {
        return password.matches("(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@#$%^&+=!])(?=\\S+$).{8,}");
    }

    private void createAccount() {
        String name = nameEditText.getText().toString().trim();
        String email = emailEditText.getText().toString().trim();
        String password = passwordEditText.getText().toString().trim();
        String repassword = repasswordEditText.getText().toString().trim();
        String room = roomno.getText().toString().trim();
        String sapId = sapid.getText().toString().trim();

        if (name.isEmpty() || email.isEmpty() || password.isEmpty() || repassword.isEmpty()) {
            Toast.makeText(CreateAccount.this, "All fields are required", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!password.equals(repassword)) {
            Toast.makeText(CreateAccount.this, "Passwords do not match", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!isValidPassword(password)) {
            Toast.makeText(CreateAccount.this, "Password is weak. Use at least 8 characters including uppercase, lowercase, digits, and special characters.", Toast.LENGTH_LONG).show();
            return;
        }

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(CreateAccount.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {

                            Toast.makeText(CreateAccount.this, "Registration successful", Toast.LENGTH_SHORT).show();
                            UserID = mAuth.getCurrentUser().getUid();

                            // Store user data in Firebase Firestore
                            DocumentReference documentReference = fStore.collection("students").document(UserID);
                            Map<String, Object> user = new HashMap<>();
                            user.put("fNmme", name);
                            user.put("email", email);
                            user.put("phone", ""); // You can add phone number here
                            user.put("SapID", sapId);
                            user.put("RoomNo", room);
                            documentReference.set(user)
                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void unused) {
                                            Log.d(TAG, "onSuccess: User profile is created for " + UserID);
                                        }
                                    })
                                    .addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(Exception e) {
                                            Log.d(TAG, "onFailure: " + e.toString());
                                        }
                                    });

                            // Store user data in Firebase Realtime Database
                            DatabaseReference usersRef = FirebaseDatabase.getInstance().getReference().child("users").child(UserID);
                            Map<String, Object> userData = new HashMap<>();
                            userData.put("name", name);
                            userData.put("email", email);
                            userData.put("room", room);
                            userData.put("sapid", sapId);
                            usersRef.setValue(userData);

                            // Navigate to login screen only if registration is successful
                            Intent intent = new Intent(CreateAccount.this, LoginPage.class);
                            startActivity(intent);
                            finish();
                        } else {
                            // Provide a user-friendly error message
                            String errorMessage = task.getException() != null ? task.getException().getLocalizedMessage() : "Registration failed";
                            Toast.makeText(CreateAccount.this, errorMessage, Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}
