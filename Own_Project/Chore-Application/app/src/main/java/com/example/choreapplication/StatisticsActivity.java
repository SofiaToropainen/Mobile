package com.example.choreapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;

import com.example.choreapplication.fragments.AllStatistics;
import com.example.choreapplication.fragments.WeekStatistics;

public class StatisticsActivity extends AppCompatActivity {

    private Button allButton, weekButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_statistics);

        allButton = findViewById(R.id.allButton);
        weekButton = findViewById(R.id.weekButton);

        allButton.setOnClickListener(listener);
        weekButton.setOnClickListener(listener);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
    private View.OnClickListener listener = v -> {
            Fragment fragment = null;

            if(v.getId() == R.id.allButton) {
                fragment = new AllStatistics();
            }

            if(v.getId() == R.id.weekButton) {
                fragment = new WeekStatistics();;
            }

            if(fragment != null) {
                changeFragment(fragment);
            }
    };

    public void changeFragment(Fragment fragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.showStatisticsFrame, fragment)
                .commit();
    }

    public void switchBackToFirstPageActivity(View view) {
        Intent intent = new Intent(this, FirstPageActivity.class);
        startActivity(intent);
    }
}