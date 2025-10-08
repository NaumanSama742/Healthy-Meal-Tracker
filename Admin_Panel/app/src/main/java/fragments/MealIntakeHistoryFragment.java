package fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.admin_panel.MealIntake;
import com.example.admin_panel.MealIntakeHistoryAdapter;
import com.example.admin_panel.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MealIntakeHistoryFragment extends Fragment {

    private RecyclerView recyclerView;
    private MealIntakeHistoryAdapter adapter;
    private List<MealIntake> mealHistoryList = new ArrayList<>();
    private DatabaseReference intakeHistoryRef;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_meal_intake_history, container, false);

        recyclerView = view.findViewById(R.id.recyclerMealIntakeHistory);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        intakeHistoryRef = FirebaseDatabase.getInstance().getReference("MealIntakeHistory");

        adapter = new MealIntakeHistoryAdapter(mealHistoryList);
        recyclerView.setAdapter(adapter);

        fetchMealHistory();

        return view;
    }

    private void fetchMealHistory() {
        intakeHistoryRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                mealHistoryList.clear();
                for (DataSnapshot ds : snapshot.getChildren()) {
                    MealIntake meal = ds.getValue(MealIntake.class);
                    if (meal != null) {
                        meal.setId(ds.getKey());
                        mealHistoryList.add(meal);
                    }
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getContext(), "Failed to load meal intake history", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
