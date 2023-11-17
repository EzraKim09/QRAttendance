package com.oomproject.qrattendance;

import android.os.Bundle;
import android.util.Log;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

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

public class AttendanceDisplayer extends AppCompatActivity {

    private Spinner spinnerClasses;
    private DatabaseReference classesReference;
    private DatabaseReference attendanceReference;
    private RecyclerView recyclerView;
    private RecyclerViewAdapter recyclerViewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendance_displayer);

        spinnerClasses = findViewById(R.id.spinnerClasses);
        recyclerView = findViewById(R.id.kimsRecylerview);
        classesReference = FirebaseDatabase.getInstance().getReference().child("Classes");
        attendanceReference = FirebaseDatabase.getInstance().getReference().child("Attendance");
        fetchClassesFromFirebase();
        setupSpinnerListener();


        recyclerView.setLayoutManager(new LinearLayoutManager(this)); // Set up the LayoutManager
        recyclerViewAdapter = new RecyclerViewAdapter(new ArrayList<>()); // Initialize an empty adapter
        recyclerView.setAdapter(recyclerViewAdapter);
    }

    private void setupSpinnerListener() {
        spinnerClasses.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, android.view.View view, int position, long id) {
                String selectedClassId = spinnerClasses.getSelectedItem().toString();
                attendanceReference.child(selectedClassId).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        List<String> itemValues = new ArrayList<>();
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            String itemValue = snapshot.getValue(String.class);
                            itemValues.add(itemValue);
                        }
                        // Process fetched timestamps as needed (display in UI or perform other operations)
                        recyclerViewAdapter = new RecyclerViewAdapter(itemValues);
                        recyclerView.setAdapter(recyclerViewAdapter);

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        Log.d("FirebaseError", databaseError.getMessage());
                    }
                });
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Handle when no item is selected
            }
        });
    }


    private void fetchClassesFromFirebase() {
        classesReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                List<String> classIDsList = new ArrayList<>();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    // Assuming "classId" is the key under each uniqueID node
                    String classId = snapshot.child("classId").getValue(String.class);
                    if (classId != null) {
                        classIDsList.add(classId);
                    }
                }

                ArrayAdapter<String> adapter = new ArrayAdapter<>(AttendanceDisplayer.this,
                        android.R.layout.simple_spinner_dropdown_item, classIDsList);
                spinnerClasses.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.d("FirebaseError", databaseError.getMessage());
            }
        });
    }
}