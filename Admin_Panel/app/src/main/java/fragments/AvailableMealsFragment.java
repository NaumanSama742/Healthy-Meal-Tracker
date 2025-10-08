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

import com.example.admin_panel.AvailableFoodAdapter;
import com.example.admin_panel.Food;
import com.example.admin_panel.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class AvailableMealsFragment extends Fragment {

    private RecyclerView recyclerView;
    private AvailableFoodAdapter adapter;
    private List<Food> foodList = new ArrayList<>();
    private DatabaseReference foodsRef;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_available_meals, container, false);

        recyclerView = view.findViewById(R.id.recyclerAvailableFoods);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        foodsRef = FirebaseDatabase.getInstance().getReference("foods");

        adapter = new AvailableFoodAdapter(foodList, new AvailableFoodAdapter.FoodActionListener() {
            @Override
            public void onEdit(Food food) {
                showEditFoodDialog(food);
            }

            @Override
            public void onDelete(Food food) {
                deleteFood(food);
            }
        });

        recyclerView.setAdapter(adapter);
        fetchFoods();

        return view;
    }

    private void fetchFoods() {
        foodsRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                foodList.clear();
                for (DataSnapshot ds : snapshot.getChildren()) {
                    Food food = ds.getValue(Food.class);
                    if (food != null) {
                        food.setId(ds.getKey());
                        foodList.add(food);
                    }
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getContext(), "Failed to load foods", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void showEditFoodDialog(Food food) {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        builder.setTitle("Edit Food Details");

        View dialogView = LayoutInflater.from(getContext()).inflate(R.layout.dialog_add_food, null);
        builder.setView(dialogView);

        EditText editMealName = dialogView.findViewById(R.id.editMealName);
        EditText editCalories = dialogView.findViewById(R.id.editCalories);
        EditText editCarbs = dialogView.findViewById(R.id.editCarbs);
        EditText editFat = dialogView.findViewById(R.id.editFat);
        EditText editProtein = dialogView.findViewById(R.id.editProtein);
        EditText editServingSize = dialogView.findViewById(R.id.editServingSize);

        editMealName.setText(food.getName());
        editCalories.setText(String.valueOf(food.getCalories()));
        editCarbs.setText(String.valueOf(food.getCarbs()));
        editFat.setText(String.valueOf(food.getFat()));
        editProtein.setText(String.valueOf(food.getProtein()));
        editServingSize.setText(food.getServing_size());

        builder.setPositiveButton("Save", (dialog, which) -> {
            food.setName(editMealName.getText().toString().trim());
            food.setCalories(parseDoubleSafe(editCalories.getText().toString()));
            food.setCarbs(parseDoubleSafe(editCarbs.getText().toString()));
            food.setFat(parseDoubleSafe(editFat.getText().toString()));
            food.setProtein(parseDoubleSafe(editProtein.getText().toString()));
            food.setServing_size(editServingSize.getText().toString().trim());

            foodsRef.child(food.getId()).setValue(food).addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    Toast.makeText(getContext(), "Food updated", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getContext(), "Failed to update food", Toast.LENGTH_SHORT).show();
                }
            });
        });

        builder.setNegativeButton("Cancel", null);

        builder.show();
    }

    private void deleteFood(Food food) {
        foodsRef.child(food.getId()).removeValue().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                Toast.makeText(getContext(), "Food deleted", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getContext(), "Failed to delete food", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private double parseDoubleSafe(String s) {
        try {
            return Double.parseDouble(s);
        } catch (Exception e) {
            return 0;
        }
    }
}
