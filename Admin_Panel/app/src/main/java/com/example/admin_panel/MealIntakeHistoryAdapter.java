package com.example.admin_panel;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MealIntakeHistoryAdapter extends RecyclerView.Adapter<MealIntakeHistoryAdapter.ViewHolder> {

    private final List<MealIntake> mealHistoryList;

    public MealIntakeHistoryAdapter(List<MealIntake> mealHistoryList) {
        this.mealHistoryList = mealHistoryList;
    }

    @NonNull
    @Override
    public MealIntakeHistoryAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_meal_intake, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MealIntakeHistoryAdapter.ViewHolder holder, int position) {
        MealIntake meal = mealHistoryList.get(position);
        holder.tvMealName.setText(meal.getMealName());
        holder.tvMealDateTime.setText(meal.getDate() + " " + meal.getTime());
        String nutrition = "Calories: " + meal.getCalories()
                + ", Carbs: " + meal.getCarbs()
                + ", Fat: " + meal.getFat()
                + ", Protein: " + meal.getProtein();
        holder.tvMealNutrition.setText(nutrition);
    }

    @Override
    public int getItemCount() {
        return mealHistoryList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvMealName, tvMealDateTime, tvMealNutrition;

        public ViewHolder(View itemView) {
            super(itemView);
            tvMealName = itemView.findViewById(R.id.tvMealName);
            tvMealDateTime = itemView.findViewById(R.id.tvMealDateTime);
            tvMealNutrition = itemView.findViewById(R.id.tvMealNutrition);
        }
    }
}
