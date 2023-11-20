package com.oomproject.qrattendance;

import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
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

public class CreatedClassesDisplayer extends AppCompatActivity {

    private DatabaseReference classesReference;
    private RecyclerView classRecyclerView;
    private RecyclerViewAdapter classRecyclerViewAdapter;
    private List<String> classList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_created_classes_displayer);

        ImageButton back = findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        classRecyclerView = findViewById(R.id.classRecycler);
        classRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        classRecyclerViewAdapter = new RecyclerViewAdapter(new ArrayList<>(), new ArrayList<>(), new RecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                showDeleteConfirmationDialog();
            }
        });

        classRecyclerView.setAdapter(classRecyclerViewAdapter);

        classesReference = FirebaseDatabase.getInstance().getReference("Classes");

        fetchDatabaseData();
    }

    private void fetchDatabaseData() {
        classesReference.orderByChild("timeStamp").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                classList.clear();
                for(DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    String classId = snapshot.getKey();
                    classList.add(classId);
                }

                classRecyclerViewAdapter.setData(classList);
                classRecyclerViewAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.d("FirebaseError", error.getMessage());
            }
        });
    }

    private void showDeleteConfirmationDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Confirm Delete");
        builder.setMessage("Delete the Instructor???");

        builder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Toast.makeText(CreatedClassesDisplayer.this, "Feature not Available Yet :)", Toast.LENGTH_SHORT).show();
            }
        });

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });

        builder.create().show();
    }
}