package com.example.hostelhelper.warden;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.hostelhelper.R;

public class Warden_dashboard extends AppCompatActivity {
    CardView cd1,cd2,cd3,cd4,cd5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_warden_dashboard);
        cd1 = findViewById(R.id.addNotice);
        cd1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Warden_dashboard.this, Adddeleteevents.class);
                startActivity(intent);
            }
        });
        cd2 = findViewById(R.id.viewcomplain);
        cd2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Warden_dashboard.this, Warden_Complain_Dashboard.class);
                startActivity(intent);

            }
        });
        cd3 = findViewById(R.id.addEbook);
        cd3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Warden_dashboard.this, Holiday_List.class);
                startActivity(intent);

            }
        });
        cd4 = findViewById(R.id.faculty);
        cd4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Warden_dashboard.this, Warden_Profile.class);
                startActivity(intent);

            }
        });
    }
}