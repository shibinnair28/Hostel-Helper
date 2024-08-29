package com.example.hostelhelper.warden;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.CheckBox;
import android.widget.Toast;

import com.example.hostelhelper.R;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ViewComplain_electrician extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ComplaintAdapter adapter;
    private List<HashMap<String, Object>> complaintList;
    private FirebaseFirestore db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_complain_electrician);

        recyclerView = findViewById(R.id.complainRV);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        complaintList = new ArrayList<>();
        adapter = new ComplaintAdapter(this, complaintList);
        recyclerView.setAdapter(adapter);

        db = FirebaseFirestore.getInstance();

        db.collection("Electrician Complains")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        for (DocumentSnapshot document : task.getResult()) {
                            HashMap<String, Object> complaint = (HashMap<String, Object>) document.getData();
                            complaintList.add(complaint);
                        }
                        adapter.notifyDataSetChanged();
                    } else {
                        Toast.makeText(ViewComplain_electrician.this, "Error getting documents: " + task.getException(), Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
