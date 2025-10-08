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

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class AdminUsersActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private UserAdapter adapter;
    private List<User> userList = new ArrayList<>();
    private DatabaseReference usersRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_users);

        recyclerView = findViewById(R.id.recyclerUsers);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        usersRef = FirebaseDatabase.getInstance().getReference("users");

        adapter = new UserAdapter(userList, new UserAdapter.UserActionListener() {
            @Override
            public void onEdit(User user) {
                showEditUserDialog(user);
            }

            @Override
            public void onDelete(User user) {
                deleteUser(user);
            }
        });

        recyclerView.setAdapter(adapter);
        fetchUsers();
    }

    private void fetchUsers() {
        usersRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                userList.clear();
                for (DataSnapshot ds : snapshot.getChildren()) {
                    User user = ds.getValue(User.class);
                    if (user != null) {
                        user.setId(ds.getKey());
                        userList.add(user);
                    }
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(AdminUsersActivity.this, "Failed to load users", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void showEditUserDialog(User user) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Edit User");

        View dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_edit_user, null);
        builder.setView(dialogView);

        EditText editName = dialogView.findViewById(R.id.editUserName);
        EditText editEmail = dialogView.findViewById(R.id.editUserEmail);
        EditText editRole = dialogView.findViewById(R.id.editUserRole);

        editName.setText(user.getName());
        editEmail.setText(user.getEmail());
        editRole.setText(user.getRole());

        builder.setPositiveButton("Save", (dialog, which) -> {
            user.setName(editName.getText().toString().trim());
            user.setEmail(editEmail.getText().toString().trim());
            user.setRole(editRole.getText().toString().trim());

            usersRef.child(user.getId()).setValue(user).addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    Toast.makeText(AdminUsersActivity.this, "User updated", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(AdminUsersActivity.this, "Failed to update user", Toast.LENGTH_SHORT).show();
                }
            });
        });

        builder.setNegativeButton("Cancel", null);

        builder.show();
    }

    private void deleteUser(User user) {
        usersRef.child(user.getId()).removeValue().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                Toast.makeText(AdminUsersActivity.this, "User deleted", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(AdminUsersActivity.this, "Failed to delete user", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
