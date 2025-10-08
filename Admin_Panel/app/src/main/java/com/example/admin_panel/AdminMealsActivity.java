package com.example.admin_panel;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

public class AdminMealsActivity extends AppCompatActivity {

    Button btnSuggestedMeals, btnAvailableMeals, btnMealIntakeHistory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_meals);

        btnSuggestedMeals = findViewById(R.id.btnSuggestedMeals);
        btnAvailableMeals = findViewById(R.id.btnAvailableMeals);
        btnMealIntakeHistory = findViewById(R.id.btnMealIntakeHistory);

        btnSuggestedMeals.setOnClickListener(v ->
                startActivity(new Intent(this, SuggestedMealsActivity.class)));

        btnAvailableMeals.setOnClickListener(v ->
                startActivity(new Intent(this, AvailableMealsActivity.class)));

        btnMealIntakeHistory.setOnClickListener(v ->
               startActivity(new Intent(this, MealIntakeHistoryActivity.class)));
    }
}
