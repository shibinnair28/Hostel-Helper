package com.example.hostelhelper.warden;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.hostelhelper.R;
import java.util.HashMap;
import java.util.List;

public class ComplaintAdapter extends RecyclerView.Adapter<ComplaintAdapter.ComplaintViewHolder> {
    private Context context;
    private List<HashMap<String, Object>> complaintList;

    public ComplaintAdapter(Context context, List<HashMap<String, Object>> complaintList) {
        this.context = context;
        this.complaintList = complaintList;
    }

    @NonNull
    @Override
    public ComplaintViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.complaint_item, parent, false);
        return new ComplaintViewHolder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull ComplaintViewHolder holder, int position) {

        int reversedPosition = complaintList.size() - 1 - position;

        HashMap<String, Object> complaint = complaintList.get(reversedPosition);

        holder.roomNumberTextView.setText((String) complaint.get("roomNumber"));
        holder.problemTextView.setText((String) complaint.get("problem"));

        // Check if the value associated with "isPending" key is not null
        Object isPendingObject = complaint.get("isPending");
        if (isPendingObject instanceof Boolean) {
            boolean isPending = (boolean) isPendingObject;
            holder.pendingCheckBox.setChecked(isPending);
            holder.completedCheckBox.setChecked(!isPending);
        } else {
            // Handle the case where "isPending" value is null or not a Boolean
            // You may set a default value or handle it based on your application's logic
        }
    }


    @Override
    public int getItemCount() {
        return complaintList.size();
    }

    public class ComplaintViewHolder extends RecyclerView.ViewHolder {
        TextView roomNumberTextView, problemTextView;
        CheckBox pendingCheckBox, completedCheckBox;

        public ComplaintViewHolder(@NonNull View itemView) {
            super(itemView);
            roomNumberTextView = itemView.findViewById(R.id.roomNumberTextView);
            problemTextView = itemView.findViewById(R.id.problemTextView);
            pendingCheckBox = itemView.findViewById(R.id.pendingCheckBox);
            completedCheckBox = itemView.findViewById(R.id.completedCheckBox);



            // Set OnCheckedChangeListener for checkboxes to handle status change
            pendingCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                    // Update complaint status when checkbox is clicked
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        // Update the status in your complaintList or database accordingly
                        updateStatus(position, isChecked);
                        completedCheckBox.setEnabled(!isChecked); // Enable completedCheckBox if pendingCheckBox is unchecked
                    }
                }
            });


            completedCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                    // Update complaint status when checkbox is clicked
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION && isChecked) {
                        // Update the status in your complaintList or database accordingly
                        updateStatus(position, false);
                        pendingCheckBox.setEnabled(false);
                    }
                }
            });
        }
    }

    private void updateStatus(int position, boolean isPending) {
        // Update the status in your complaintList or database accordingly
        // For simplicity, let's just show a toast message indicating the status change
        HashMap<String, Object> complaint = complaintList.get(position);
        complaint.put("isPending", isPending);
        String status = isPending ? "Pending" : "Completed";
        Toast.makeText(context, "Complaint status changed to " + status, Toast.LENGTH_SHORT).show();
    }
}
