package com.example.admin_panel;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class SuggestedMealAdapter extends RecyclerView.Adapter<SuggestedMealAdapter.ViewHolder> {

    public interface SuggestedMealActionListener {
        void onAddMeal(SuggestedMeal meal);
        void onDeleteMeal(SuggestedMeal meal);
    }

    private final List<SuggestedMeal> meals;
    private final SuggestedMealActionListener listener;

    public SuggestedMealAdapter(List<SuggestedMeal> meals, SuggestedMealActionListener listener) {
        this.meals = meals;
        this.listener = listener;
    }

    @NonNull
    @Override
    public SuggestedMealAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_suggested_meal, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull SuggestedMealAdapter.ViewHolder holder, int position) {
        SuggestedMeal meal = meals.get(position);
        holder.tvMealName.setText(meal.getName());

        holder.btnAddMeal.setOnClickListener(v -> {
            if (listener != null) listener.onAddMeal(meal);
        });

        holder.btnDeleteMeal.setOnClickListener(v -> {
            if (listener != null) listener.onDeleteMeal(meal);
        });
    }

    @Override
    public int getItemCount() {
        return meals.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvMealName;
        Button btnAddMeal, btnDeleteMeal;

        public ViewHolder(View itemView) {
            super(itemView);
            tvMealName = itemView.findViewById(R.id.tvMealName);
            btnAddMeal = itemView.findViewById(R.id.btnAddMeal);
            btnDeleteMeal = itemView.findViewById(R.id.btnDeleteMeal);
        }
    }
}
