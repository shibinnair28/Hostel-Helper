package com.example.hostelhelper.student;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hostelhelper.LoginPage;
import com.example.hostelhelper.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Profile extends AppCompatActivity {
    private FirebaseUser user;
    private DatabaseReference reference;
    private String userId;
    private Button logoutButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        user = FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference("users");
        userId = user.getUid();


        final TextView fullNameTextView = findViewById(R.id.textView);
        final TextView emailTextView = findViewById(R.id.textView2);
        final TextView roomtextview = findViewById(R.id.textView8);
        final TextView SapTextView = findViewById(R.id.textView4);

        reference.child(userId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    String fullName = snapshot.child("name").getValue(String.class);
                    String email = snapshot.child("email").getValue(String.class);
                    String room = snapshot.child("room").getValue(String.class); // Use correct key
                    String sap = snapshot.child("sapid").getValue(String.class); // Use correct key


                    fullNameTextView.setText(fullName);
                    emailTextView.setText(email);
                    roomtextview.setText(room);
                    SapTextView.setText(sap);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(Profile.this, "Error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        //Bottom Navigation
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setSelectedItemId(R.id.bottom_profile);

        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            int itemId = item.getItemId();
            if (itemId == R.id.bottom_home) {
                startActivity(new Intent(getApplicationContext(), feedpage_student.class));
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
                finish();
                return true;
            } else if (itemId == R.id.bottom_Complain) {
                startActivity(new Intent(getApplicationContext(), Complain_dashboard.class));
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
                finish();

                return true;

            } else if (itemId == R.id.bottom_profile) {
                return true;
            }
            return false;
        });


        logoutButton = findViewById(R.id.button);
        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(Profile.this, LoginPage.class));
                finish();
            }
        });
    }}
