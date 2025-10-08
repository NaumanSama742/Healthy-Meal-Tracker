package com.example.admin_panel;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class CoachAdapter extends RecyclerView.Adapter<CoachAdapter.ViewHolder> {

    public interface CoachActionListener {
        void onEdit(Coach coach);
        void onDelete(Coach coach);
    }

    private final List<Coach> coachList;
    private final CoachActionListener listener;

    public CoachAdapter(List<Coach> coachList, CoachActionListener listener) {
        this.coachList = coachList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public CoachAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_coach, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull CoachAdapter.ViewHolder holder, int position) {
        Coach coach = coachList.get(position);

        holder.tvCoachName.setText(coach.getName());
        holder.tvCoachEmail.setText(coach.getEmail());
        holder.tvCoachPhone.setText(coach.getPhone());

        holder.btnEditCoach.setOnClickListener(v -> {
            if (listener != null) listener.onEdit(coach);
        });

        holder.btnDeleteCoach.setOnClickListener(v -> {
            if (listener != null) listener.onDelete(coach);
        });
    }

    @Override
    public int getItemCount() {
        return coachList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvCoachName, tvCoachEmail, tvCoachPhone;
        Button btnEditCoach, btnDeleteCoach;

        public ViewHolder(View itemView) {
            super(itemView);
            tvCoachName = itemView.findViewById(R.id.tvCoachName);
            tvCoachEmail = itemView.findViewById(R.id.tvCoachEmail);
            tvCoachPhone = itemView.findViewById(R.id.tvCoachPhone);
            btnEditCoach = itemView.findViewById(R.id.btnEditCoach);
            btnDeleteCoach = itemView.findViewById(R.id.btnDeleteCoach);
        }
    }
}
