// feedpage_student.java

package com.example.hostelhelper.student;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hostelhelper.R;
import com.example.hostelhelper.warden.Adddeleteevents;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

public class feedpage_student extends AppCompatActivity {

    private RecyclerView recyclerView;
    private NoticeAdapter adapter;
    private FirebaseFirestore db;

    FirebaseAuth auth;

    public class Notice {
        private final String eventName;
        private String description;
        private String date;

        public Notice(String eventName, String description, String date) {
            this.eventName = eventName;
            this.description = description;
            this.date = date;
        }

        public String getEventName() {
            return eventName;
        }

        public String getDescription() {
            return description;
        }

        public String getDate() {
            return date;
        }
    }

    public class NoticeAdapter extends RecyclerView.Adapter<NoticeAdapter.NoticeViewHolder> {
        private List<Notice> notices;

        public NoticeAdapter(List<Notice> notices) {
            this.notices = notices;
        }

        @NonNull
        @Override
        public NoticeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.notice_item, parent, false);
            return new NoticeViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull NoticeViewHolder holder, int position) {
            Notice notice = notices.get(position);
            holder.text_event_name.setText(notice.getEventName());
            holder.text_description.setText(notice.getDescription());
            holder.text_date.setText(notice.getDate());


            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            DatabaseReference reference = FirebaseDatabase.getInstance().getReference("users");
            String userId = user.getUid();


            final TextView fullNameTextView = findViewById(R.id.name123);

            reference.child(userId).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (snapshot.exists()) {
                        String fullName = snapshot.child("name").getValue(String.class);



                        fullNameTextView.setText("Hello, "+fullName);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    Toast.makeText(feedpage_student.this, "Error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });




        }

        @Override
        public int getItemCount() {
            return notices.size();
        }

        public class NoticeViewHolder extends RecyclerView.ViewHolder {
            TextView text_event_name;
            TextView text_description;
            TextView text_date;
            CardView cardView;

            public NoticeViewHolder(@NonNull View itemView) {
                super(itemView);
                text_event_name = itemView.findViewById(R.id.text_event_name);
                text_description = itemView.findViewById(R.id.text_description);
                text_date = itemView.findViewById(R.id.text_date);
                cardView = itemView.findViewById(R.id.cardView);
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedpage_student);

        db = FirebaseFirestore.getInstance();

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setSelectedItemId(R.id.bottom_home);

        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            int itemId = item.getItemId();
            if (itemId == R.id.bottom_home) {
                return true;
            } else if (itemId == R.id.bottom_Complain) {
                startActivity(new Intent(getApplicationContext(), Complain_dashboard.class));
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left); // Slide in from right, slide out to left
                finish();
                return true;
            } else if (itemId == R.id.bottom_profile) {
                startActivity(new Intent(getApplicationContext(), Profile.class));
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left); // Slide in from left, slide out to right
                finish();
                return true;
            }
            return false;
        });

        fetchNoticesFromFirestore();
    }

    private void fetchNoticesFromFirestore() {
        db.collection("Notices")
                .orderBy("date", Query.Direction.ASCENDING) // Sort events by date in ascending order
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        List<Notice> notices = new ArrayList<>();
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            String eventName = document.getId();
                            String description = document.getString("description");
                            String date = document.getString("date");
                            notices.add(new Notice(eventName, description, date));
                        }
                        adapter = new NoticeAdapter(notices);
                        recyclerView.setAdapter(adapter);
                    } else {
                        Toast.makeText(feedpage_student.this, "Failed to fetch notices", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}

