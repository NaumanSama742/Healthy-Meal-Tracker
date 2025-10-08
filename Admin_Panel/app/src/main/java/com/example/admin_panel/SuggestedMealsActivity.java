package com.example.admin_panel;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import fragments.SuggestedMealsFragment;

public class SuggestedMealsActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simple_container);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, new SuggestedMealsFragment())
                    .commit();
        }
    }
}
