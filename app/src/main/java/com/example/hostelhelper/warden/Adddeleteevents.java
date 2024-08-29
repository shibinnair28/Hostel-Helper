package com.example.hostelhelper.warden;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.hostelhelper.R;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class Adddeleteevents extends AppCompatActivity {

    private EditText eventNameEditText;
    private EditText dateEditText;
    private EditText descriptionEditText;
    private Button addEventButton;
    private Button deleteEventButton;

    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adddeleteevents);

        db = FirebaseFirestore.getInstance();

        // Initialize views
        eventNameEditText = findViewById(R.id.Noticename);
        dateEditText = findViewById(R.id.Date);
        descriptionEditText = findViewById(R.id.Description);
        addEventButton = findViewById(R.id.buttonAddNotice);
        deleteEventButton = findViewById(R.id.buttonDeleteNotice);

        // Set OnClickListener to add event button
        addEventButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get input data from EditTexts
                String eventName = eventNameEditText.getText().toString().trim();
                String date = dateEditText.getText().toString().trim();
                String description = descriptionEditText.getText().toString().trim();

                // Check if any field is empty
                if (eventName.isEmpty() || date.isEmpty() || description.isEmpty()) {
                    // Show toast message indicating fields are empty
                    Toast.makeText(Adddeleteevents.this, "Please fill all fields", Toast.LENGTH_SHORT).show();
                } else {
                    // Add event to Firestore
                    addEventToFirestore(eventName, date, description);
                }
            }
        });

        // Set OnClickListener to delete event button
        deleteEventButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get input data from EditTexts
                String eventName = eventNameEditText.getText().toString().trim();
                String date = dateEditText.getText().toString().trim();

                // Check if any field is empty
                if (eventName.isEmpty() || date.isEmpty()) {
                    // Show toast message indicating fields are empty
                    Toast.makeText(Adddeleteevents.this, "Please enter event name and date", Toast.LENGTH_SHORT).show();
                } else {
                    // Delete event from Firestore
                    deleteEventFromFirestore(eventName, date);
                }
            }
        });
    }

    private void addEventToFirestore(String eventName, String date, String description) {
        // Create a new event map
        Map<String, Object> eventData = new HashMap<>();
        eventData.put("date", date);
        eventData.put("description", description);
        eventData.put("timestamp", FieldValue.serverTimestamp()); // Add timestamp field

        // Add event to Firestore
        db.collection("Notices")
                .document(eventName) // Use event name as document ID
                .set(eventData)
                .addOnSuccessListener(aVoid -> {
                    // Event added successfully
                    Toast.makeText(Adddeleteevents.this, "Event added successfully", Toast.LENGTH_SHORT).show();
                    clearInputFields();
                })
                .addOnFailureListener(e -> {
                    // Failed to add event
                    Toast.makeText(Adddeleteevents.this, "Failed to add event", Toast.LENGTH_SHORT).show();
                });
    }

    private void deleteEventFromFirestore(String eventName, String date) {
        // Delete event from Firestore
        db.collection("Notices")
                .document(eventName) // Use event name as document ID
                .delete()
                .addOnSuccessListener(aVoid -> {
                    // Event deleted successfully
                    Toast.makeText(Adddeleteevents.this, "Event deleted successfully", Toast.LENGTH_SHORT).show();
                    clearInputFields();
                })
                .addOnFailureListener(e -> {
                    // Failed to delete event
                    Toast.makeText(Adddeleteevents.this, "Failed to delete event", Toast.LENGTH_SHORT).show();
                });
    }

    private void clearInputFields() {
        eventNameEditText.setText("");
        dateEditText.setText("");
        descriptionEditText.setText("");
    }
}
