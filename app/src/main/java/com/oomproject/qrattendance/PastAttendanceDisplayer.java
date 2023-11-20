package com.oomproject.qrattendance;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class PastAttendanceDisplayer extends AppCompatActivity {

    private DatabaseReference attendanceReference;
    private RecyclerView recyclerView;
    private RecyclerViewAdapter recyclerViewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_past_attendance_displayer);

        ImageButton back = findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewAdapter = new RecyclerViewAdapter(new ArrayList<>(), null, null);
        recyclerView.setAdapter(recyclerViewAdapter);


        attendanceReference = FirebaseDatabase.getInstance().getReference().child("Attendance");

        fetchClassesData();
    }

    private void fetchClassesData() {
        attendanceReference.addListenerForSingleValueEvent(new ValueEventListener() {
            String userId = identifyStudent();
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                List<String> classList = new ArrayList<>();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    String classId = snapshot.getKey();
                    boolean studentAttendance = false;

                    for (DataSnapshot presentSnapshot : snapshot.getChildren()) {
                        String presentStudentId = presentSnapshot.getKey();
                        if (presentStudentId != null && presentStudentId.equals(userId)) {
                            studentAttendance = true;
                            break;
                        }
                    }

                    if (classId != null) {
                        if (studentAttendance) {
                            classList.add("Class ID: " + classId + "    Status: Present");
                        } else {
                            classList.add("Class ID: " + classId + "    Status: Absent");
                        }
                    }
                }

                recyclerViewAdapter.setData(classList);
                recyclerViewAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.d("FirebaseError", databaseError.getMessage());
            }
        });
    }

    private String identifyStudent() {
        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        if (firebaseUser != null) {
            String userEmail = firebaseUser.getEmail();
            if (userEmail != null) {
                int atIndex = userEmail.indexOf('@');
                if (atIndex != -1) {
                    return userEmail.substring(0, atIndex);
                } else {
                    return "unknown";
                }
            } else {
                return "unknown";
            }
        } else {
            return "unknown";
        }
    }
}