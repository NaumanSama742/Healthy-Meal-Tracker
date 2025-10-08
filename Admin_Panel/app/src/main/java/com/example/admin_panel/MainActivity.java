package com.example.admin_panel;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private Button btnManageMeals, btnManageUsers, btnManageCoaches;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnManageMeals = findViewById(R.id.btnManageMeals);
        btnManageUsers = findViewById(R.id.btnManageUsers);
        btnManageCoaches = findViewById(R.id.btnManageCoaches);

        btnManageMeals.setOnClickListener(v -> {
            // Navigate to AdminMealsActivity or Manage Meals main screen
            startActivity(new Intent(this, AdminMealsActivity.class));
        });

        btnManageUsers.setOnClickListener(v -> {
            // Navigate to User management activity
            startActivity(new Intent(this, AdminUsersActivity.class));
        });

        btnManageCoaches.setOnClickListener(v -> {
            // Navigate to Coach management activity
            startActivity(new Intent(this, AdminCoachesActivity.class));
        });
    }
}
