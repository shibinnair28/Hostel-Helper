package com.example.hostelhelper.warden;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.hostelhelper.R;

public class Warden_Complain_Dashboard extends AppCompatActivity {
    ImageView iv1,iv2,iv3;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_warden_complain_dashboard);
        iv1 = findViewById(R.id.electrician);
        iv1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(Warden_Complain_Dashboard.this, ViewComplain_electrician.class);
                startActivity(in);
            }
        });
        iv2 = findViewById(R.id.carpenter);
        iv2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(Warden_Complain_Dashboard.this, ViewComplain_carpenter.class);
                startActivity(in);
            }
        });
        iv3 = findViewById(R.id.plumber);
        iv3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(Warden_Complain_Dashboard.this, ViewComplain_Plumber.class);
                startActivity(in);
            }
        });

    }
}