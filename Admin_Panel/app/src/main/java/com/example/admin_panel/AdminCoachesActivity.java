package com.example.admin_panel;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.firebase.database.*;
import java.util.ArrayList;
import java.util.List;

public class AdminCoachesActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private CoachAdapter adapter;
    private List<Coach> coachList = new ArrayList<>();
    private DatabaseReference coachesRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_coaches);

        recyclerView = findViewById(R.id.recyclerCoaches);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        coachesRef = FirebaseDatabase.getInstance().getReference("coaches");

        adapter = new CoachAdapter(coachList, new CoachAdapter.CoachActionListener() {
            @Override
            public void onEdit(Coach coach) {
                showEditCoachDialog(coach);
            }

            @Override
            public void onDelete(Coach coach) {
                deleteCoach(coach);
            }
        });

        recyclerView.setAdapter(adapter);
        fetchCoaches();
    }

    private void fetchCoaches() {
        coachesRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                coachList.clear();
                for (DataSnapshot ds : snapshot.getChildren()) {
                    Coach coach = ds.getValue(Coach.class);
                    if (coach != null) {
                        coach.setId(ds.getKey());
                        coachList.add(coach);
                    }
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(AdminCoachesActivity.this, "Failed to load coaches", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void showEditCoachDialog(Coach coach) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Edit Coach");

        View dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_edit_coach, null);
        builder.setView(dialogView);

        EditText editName = dialogView.findViewById(R.id.editCoachName);
        EditText editEmail = dialogView.findViewById(R.id.editCoachEmail);
        EditText editPhone = dialogView.findViewById(R.id.editCoachPhone);

        editName.setText(coach.getName());
        editEmail.setText(coach.getEmail());
        editPhone.setText(coach.getPhone());

        builder.setPositiveButton("Save", (dialog, which) -> {
            coach.setName(editName.getText().toString().trim());
            coach.setEmail(editEmail.getText().toString().trim());
            coach.setPhone(editPhone.getText().toString().trim());

            coachesRef.child(coach.getId()).setValue(coach).addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    Toast.makeText(AdminCoachesActivity.this, "Coach updated", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(AdminCoachesActivity.this, "Failed to update coach", Toast.LENGTH_SHORT).show();
                }
            });
        });

        builder.setNegativeButton("Cancel", null);

        builder.show();
    }

    private void deleteCoach(Coach coach) {
        coachesRef.child(coach.getId()).removeValue().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                Toast.makeText(AdminCoachesActivity.this, "Coach deleted", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(AdminCoachesActivity.this, "Failed to delete coach", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
