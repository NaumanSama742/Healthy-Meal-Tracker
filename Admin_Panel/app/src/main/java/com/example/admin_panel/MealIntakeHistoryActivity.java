package com.example.admin_panel;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import fragments.MealIntakeHistoryFragment;

public class MealIntakeHistoryActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simple_container);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, new MealIntakeHistoryFragment())
                    .commit();
        }
    }
}
