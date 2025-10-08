package com.example.admin_panel;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class AvailableFoodAdapter extends RecyclerView.Adapter<AvailableFoodAdapter.ViewHolder> {

    public interface FoodActionListener {
        void onEdit(Food food);
        void onDelete(Food food);
    }

    private final List<Food> foodList;
    private final FoodActionListener listener;

    public AvailableFoodAdapter(List<Food> foodList, FoodActionListener listener) {
        this.foodList = foodList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public AvailableFoodAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_available_food, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull AvailableFoodAdapter.ViewHolder holder, int position) {
        Food food = foodList.get(position);

        holder.tvFoodName.setText(food.getName());
        String details = "Calories: " + food.getCalories() +
                ", Carbs: " + food.getCarbs() +
                ", Fat: " + food.getFat() +
                ", Protein: " + food.getProtein() +
                ", Serving: " + food.getServing_size();
        holder.tvFoodDetails.setText(details);

        holder.btnEditFood.setOnClickListener(v -> {
            if (listener != null) listener.onEdit(food);
        });

        holder.btnDeleteFood.setOnClickListener(v -> {
            if (listener != null) listener.onDelete(food);
        });
    }

    @Override
    public int getItemCount() {
        return foodList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvFoodName, tvFoodDetails;
        Button btnEditFood, btnDeleteFood;

        public ViewHolder(View itemView) {
            super(itemView);
            tvFoodName = itemView.findViewById(R.id.tvFoodName);
            tvFoodDetails = itemView.findViewById(R.id.tvFoodDetails);
            btnEditFood = itemView.findViewById(R.id.btnEditFood);
            btnDeleteFood = itemView.findViewById(R.id.btnDeleteFood);
        }
    }
}
