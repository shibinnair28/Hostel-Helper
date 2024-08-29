package com.example.hostelhelper.warden;

import android.content.res.Resources;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.hostelhelper.R;

public class Holiday_List extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_holiday_list);

        Resources res = getResources();

        ListView januaryListView = findViewById(R.id.januaryListView);
        ArrayAdapter<String> januaryAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, res.getStringArray(R.array.january_2024));
        januaryListView.setAdapter(januaryAdapter);

        // Populating ListView for February
        ListView februaryListView = findViewById(R.id.februaryListView);
        ArrayAdapter<String> februaryAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, res.getStringArray(R.array.february_2024));
        februaryListView.setAdapter(februaryAdapter);

// Populating ListView for March
        ListView marchListView = findViewById(R.id.marchListView);
        ArrayAdapter<String> marchAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, res.getStringArray(R.array.march_2024));
        marchListView.setAdapter(marchAdapter);

// Populating ListView for April
        ListView aprilListView = findViewById(R.id.aprilListView);
        ArrayAdapter<String> aprilAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, res.getStringArray(R.array.april_2024));
        aprilListView.setAdapter(aprilAdapter);

// Populating ListView for May
        ListView mayListView = findViewById(R.id.mayListView);
        ArrayAdapter<String> mayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, res.getStringArray(R.array.may_2024));
        mayListView.setAdapter(mayAdapter);

// Populating ListView for July
        ListView julyListView = findViewById(R.id.julyListView);
        ArrayAdapter<String> julyAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, res.getStringArray(R.array.july_2024));
        julyListView.setAdapter(julyAdapter);

// Populating ListView for August
        ListView augustListView = findViewById(R.id.augustListView);
        ArrayAdapter<String> augustAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, res.getStringArray(R.array.august_2024));
        augustListView.setAdapter(augustAdapter);

// Populating ListView for September
        ListView septemberListView = findViewById(R.id.septemberListView);
        ArrayAdapter<String> septemberAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, res.getStringArray(R.array.september_2024));
        septemberListView.setAdapter(septemberAdapter);

// Populating ListView for October
        ListView octoberListView = findViewById(R.id.octoberListView);
        ArrayAdapter<String> octoberAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, res.getStringArray(R.array.october_2024));
        octoberListView.setAdapter(octoberAdapter);

// Populating ListView for November
        ListView novemberListView = findViewById(R.id.novemberListView);
        ArrayAdapter<String> novemberAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, res.getStringArray(R.array.november_2024));
        novemberListView.setAdapter(novemberAdapter);

// Populating ListView for December
        ListView decemberListView = findViewById(R.id.decemberListView);
        ArrayAdapter<String> decemberAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, res.getStringArray(R.array.december_2024));
        decemberListView.setAdapter(decemberAdapter);

    }
}
