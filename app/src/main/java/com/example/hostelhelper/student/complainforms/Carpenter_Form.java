package com.example.hostelhelper.student.complainforms;

import static android.content.ContentValues.TAG;

import android.app.Dialog;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.hostelhelper.R;
import com.example.hostelhelper.student.Complain_dashboard.Complaint;
import com.google.firebase.firestore.FirebaseFirestore;

public class Carpenter_Form extends AppCompatActivity {
    private EditText roomNumberEditText;
    private EditText problemEditText;
    private Button submitButton;
    private String room;
    private String complain;

    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_carpenter_form);

        // Initialize Firebase Firestore
        db = FirebaseFirestore.getInstance();

        // Initialize views
        roomNumberEditText = findViewById(R.id.roomnoedittext);
        problemEditText = findViewById(R.id.problem);
        submitButton = findViewById(R.id.submit);

        // Set OnClickListener to submit button
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get room number and problem from EditTexts
                String roomNumber = roomNumberEditText.getText().toString().trim();
                String problem = problemEditText.getText().toString().trim();

                // Check if any field is empty
                if (TextUtils.isEmpty(roomNumber) || TextUtils.isEmpty(problem)) {
                    // Show toast message indicating fields are empty
                    Toast.makeText(Carpenter_Form.this, "Please fill all fields", Toast.LENGTH_SHORT).show();
                } else {
                    // Call method to add complaint data to Firestore
                    addComplaintDataToFirestore(roomNumber, problem);

                }
            }
        });
    }



    private void addComplaintDataToFirestore(String roomNumber, String problem) {
        // Create a complaint object
        Complaint complaint = new Complaint(roomNumber, problem, "Carpenter");

        // Add data to Firestore collection "CarpenterComplaints"
        db.collection("CarpenterComplaints")
                .add(complaint)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        // Data storage success
                        showDialog();
                    } else {
                        // Data storage failed
                        Exception e = task.getException();
                        Log.e(TAG, "Error storing data to Firestore", e);
                        Toast.makeText(Carpenter_Form.this, "Failed to submit: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    // Show dialog upon successful data submission
    private void showDialog() {
        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.activity_dialogbox);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        dialog.getWindow().setWindowAnimations(R.style.animationfordialog);
        Button close = dialog.findViewById(R.id.close);
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
                clearFormData(); // Clear form data
            }
        });
        dialog.show();
    }

    // Method to clear form data
    private void clearFormData() {
        roomNumberEditText.getText().clear();
        problemEditText.getText().clear();
    }
}
