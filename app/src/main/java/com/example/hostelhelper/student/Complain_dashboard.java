package com.example.hostelhelper.student;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;

import com.example.hostelhelper.R;

import com.example.hostelhelper.student.complainforms.Carpenter_Form;
import com.example.hostelhelper.student.complainforms.Electrician_Form;
import com.example.hostelhelper.student.complainforms.Plumber_Form;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class Complain_dashboard extends AppCompatActivity {

    @SuppressLint("NonConstantResourceId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comaplain_dashboard);
        CardView electricianButton = findViewById(R.id.electrician);
        CardView plumberButton = findViewById(R.id.plumber);
        CardView carpenterButton = findViewById(R.id.carpenter);

// Set click listeners for each ImageButton
        electricianButton.setOnClickListener(view -> {
            // Open ElectricianActivity
            Intent intent = new Intent(Complain_dashboard.this, Electrician_Form.class);
            startActivity(intent);
        });

        plumberButton.setOnClickListener(view -> {
            // Open PlumberActivity
            Intent intent = new Intent(Complain_dashboard.this, Plumber_Form.class);
            startActivity(intent);
        });

        carpenterButton.setOnClickListener(view -> {
            // Open CarpenterActivity
            Intent intent = new Intent(Complain_dashboard.this, Carpenter_Form.class);
            startActivity(intent);
        });



        //Bottom Navigation
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setSelectedItemId(R.id.bottom_Complain);

        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            int itemId = item.getItemId();
            if (itemId == R.id.bottom_home) {
                startActivity(new Intent(getApplicationContext(), feedpage_student.class));
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
                finish();
                return true;
            } else if (itemId == R.id.bottom_Complain) {
                finish();

                return true;
            } else if (itemId == R.id.bottom_profile) {
                startActivity(new Intent(getApplicationContext(), Profile.class));
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                finish();
                return true;
            }
            return false;
        });
    }

    // Inside the Comaplain_dashboard activity
    // Inside the Comaplain_dashboard activity
    public static class Complaint {
        private String roomNumber;
        private String problem;
        private String category;

        public Complaint() {
            // Default constructor required for Firestore
        }

        public Complaint(String roomNumber, String problem, String category) {
            this.roomNumber = roomNumber;
            this.problem = problem;
            this.category = category;
        }

        public String getRoomNumber() {
            return roomNumber;
        }

        public void setRoomNumber(String roomNumber) {
            this.roomNumber = roomNumber;
        }

        public String getProblem() {
            return problem;
        }

        public void setProblem(String problem) {
            this.problem = problem;
        }

        public String getCategory() {
            return category;
        }

        public void setCategory(String category) {
            this.category = category;
        }
    }

}


