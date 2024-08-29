package com.example.hostelhelper;

import android.content.Intent;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.UnderlineSpan;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.hostelhelper.student.feedpage_student;
import com.example.hostelhelper.warden.Warden_dashboard;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class LoginPage extends AppCompatActivity {
    EditText e1, e2;
    Button b1, b2;
    TextView t1, t2,t3;
    ProgressBar progressBar; // Progress bar
    FirebaseAuth mAuth;
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loginpage);

        e1 = findViewById(R.id.username);
        e2 = findViewById(R.id.password);
        b1 = findViewById(R.id.loginButton);
        b2 = findViewById(R.id.wardenButton);
        t1 = findViewById(R.id.signupText);
        SpannableString co = new SpannableString("SignUp");
        co.setSpan(new UnderlineSpan(), 0, co.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        t1.setText(co);
        t2 = findViewById(R.id.loginText);
        t3 = findViewById(R.id.forgotPasswordTextView);
        SpannableString content = new SpannableString("Forgot Password");
        content.setSpan(new UnderlineSpan(), 0, content.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        t3.setText(content);
        progressBar = findViewById(R.id.progressBar); // Initialize ProgressBar
        progressBar.setVisibility(View.INVISIBLE); // Hide initially
        t2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(LoginPage.this, feedpage_student.class);
                startActivity(in);
            }

        });

        t3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(LoginPage.this, Forgot_Password.class);
                startActivity(in);
            }
        });

        mAuth = FirebaseAuth.getInstance();

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String email = e1.getText().toString().trim();
                final String password = e2.getText().toString();

                // Check if email or password is empty
                if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password)) {
                    Toast.makeText(LoginPage.this, "Please enter email and password", Toast.LENGTH_SHORT).show();
                    return; // Exit the method if either field is empty
                }

                // Show ProgressBar
                progressBar.setVisibility(View.VISIBLE);

                // Attempt student login
                mAuth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                // Hide ProgressBar
                                progressBar.setVisibility(View.INVISIBLE);

                                if (task.isSuccessful()) {
                                    Toast.makeText(LoginPage.this, "Student Login Successful", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(LoginPage.this, feedpage_student.class);
                                    startActivity(intent);
                                } else {
                                    // If sign in fails, display a message to the user.
                                    Toast.makeText(LoginPage.this, "Authentication failed.", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });

        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String email = e1.getText().toString().trim();
                final String password = e2.getText().toString();

                // Check if email or password is empty
                if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password)) {
                    Toast.makeText(LoginPage.this, "Please enter email and password", Toast.LENGTH_SHORT).show();
                    return; // Exit the method if either field is empty
                }

                // Show ProgressBar
                progressBar.setVisibility(View.VISIBLE);

                // Check if the login is attempted by the warden
                if (email.equals("warden@nmims.edu") && password.equals("Warden@123")) {
                    // Warden login
                    mAuth.signInWithEmailAndPassword(email, password)
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    // Hide ProgressBar
                                    progressBar.setVisibility(View.INVISIBLE);

                                    if (task.isSuccessful()) {
                                        Toast.makeText(LoginPage.this, "Warden login successful", Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(LoginPage.this, Warden_dashboard.class);
                                        startActivity(intent);
                                    } else {
                                        // If sign in fails, display a message to the user.
                                        Toast.makeText(LoginPage.this, "Authentication failed.", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                } else {
                    // Other login attempts (student, etc.)
                    mAuth.signInWithEmailAndPassword(email, password)
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    // Hide ProgressBar
                                    progressBar.setVisibility(View.INVISIBLE);

                                    if (task.isSuccessful()) {
                                        Toast.makeText(LoginPage.this, "Authentication failed.", Toast.LENGTH_SHORT).show();

                                    } else {
                                        // If sign in fails, display a message to the user.
                                        Toast.makeText(LoginPage.this, "Authentication failed.", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                }
            }
        });

        t1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginPage.this, CreateAccount.class);
                startActivity(intent);
            }
        });
    }
}
