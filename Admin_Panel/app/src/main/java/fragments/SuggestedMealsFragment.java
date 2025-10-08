package fragments;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.admin_panel.R;
import com.example.admin_panel.SuggestedMeal;
import com.example.admin_panel.SuggestedMealAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SuggestedMealsFragment extends Fragment {

    private RecyclerView recyclerView;
    private SuggestedMealAdapter adapter;
    private List<SuggestedMeal> mealList = new ArrayList<>();
    private DatabaseReference suggestionsRef;
    private DatabaseReference mealsRef;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_suggested_meals, container, false);

        recyclerView = view.findViewById(R.id.recyclerSuggestedMeals);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        suggestionsRef = FirebaseDatabase.getInstance().getReference("UserSuggestions");
        mealsRef = FirebaseDatabase.getInstance().getReference("meals");

        adapter = new SuggestedMealAdapter(mealList, new SuggestedMealAdapter.SuggestedMealActionListener() {
            @Override
            public void onAddMeal(SuggestedMeal meal) {
                showAddMealDialog(meal);
            }

            @Override
            public void onDeleteMeal(SuggestedMeal meal) {
                deleteSuggestedMeal(meal);
            }
        });

        recyclerView.setAdapter(adapter);

        fetchSuggestedMeals();

        return view;
    }

    private void fetchSuggestedMeals() {
        suggestionsRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                mealList.clear();
                for (DataSnapshot ds : snapshot.getChildren()) {
                    SuggestedMeal meal = new SuggestedMeal();
                    meal.setId(ds.getKey());
                    meal.setName(ds.getValue(String.class));
                    mealList.add(meal);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getContext(), "Failed to load suggested meals", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void showAddMealDialog(SuggestedMeal suggestedMeal) {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        builder.setTitle("Add Meal Details");
        View dialogView = LayoutInflater.from(getContext()).inflate(R.layout.dialog_add_food, null);
        builder.setView(dialogView);

        EditText editMealName = dialogView.findViewById(R.id.editMealName);
        EditText editCalories = dialogView.findViewById(R.id.editCalories);
        EditText editCarbs = dialogView.findViewById(R.id.editCarbs);
        EditText editFat = dialogView.findViewById(R.id.editFat);
        EditText editProtein = dialogView.findViewById(R.id.editProtein);
        EditText editServingSize = dialogView.findViewById(R.id.editServingSize);

        editMealName.setText(suggestedMeal.getName());

        builder.setPositiveButton("Add", (dialog, which) -> {
            String name = editMealName.getText().toString().trim();
            double calories = parseDouble(editCalories.getText().toString());
            double carbs = parseDouble(editCarbs.getText().toString());
            double fat = parseDouble(editFat.getText().toString());
            double protein = parseDouble(editProtein.getText().toString());
            String servingSize = editServingSize.getText().toString().trim();

            String key = mealsRef.push().getKey();
            if (key != null) {
                Map<String, Object> mealData = new HashMap<>();
                mealData.put("name", name);
                mealData.put("calories", calories);
                mealData.put("carbs", carbs);
                mealData.put("fat", fat);
                mealData.put("protein", protein);
                mealData.put("serving_size", servingSize);

                mealsRef.child(key).setValue(mealData).addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Toast.makeText(getContext(), "Meal added", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getContext(), "Failed to add meal", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        builder.setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss());

        builder.show();
    }

    private void deleteSuggestedMeal(SuggestedMeal meal) {
        if (meal.getId() != null) {
            suggestionsRef.child(meal.getId()).removeValue().addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    Toast.makeText(getContext(), "Suggested meal deleted", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getContext(), "Failed to delete suggested meal", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    private double parseDouble(String value) {
        try {
            return Double.parseDouble(value);
        } catch (NumberFormatException e) {
            return 0;
        }
    }
}
