package com.oomproject.qrattendance;

import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
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

public class CreatedAccountsDisplayer extends AppCompatActivity {

    private DatabaseReference instructorReference;
    private DatabaseReference adminReference;
    private RecyclerView instructorRecyclerView;
    private RecyclerView adminRecyclerView;
    private RecyclerViewAdapter instructorAdapter;
    private RecyclerViewAdapter adminAdapter;
    private List<String> instructorList = new ArrayList<>();
    private List<String> instructorIds = new ArrayList<>();
    private List<String> adminList = new ArrayList<>();
    private List<String> adminIds = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_created_accounts_displayer);

        instructorRecyclerView = findViewById(R.id.instructorRecycler);
        instructorRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        instructorAdapter = new RecyclerViewAdapter(new ArrayList<>(), new ArrayList<>(), new RecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int i) {
                showDeleteConfirmationDialog();
            }
        });
        instructorRecyclerView.setAdapter(instructorAdapter);

        instructorReference = FirebaseDatabase.getInstance().getReference("Instructors");

        fetchDatabaseData(1);

        adminRecyclerView = findViewById(R.id.adminRecycler);
        adminRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        adminAdapter = new RecyclerViewAdapter(new ArrayList<>(), new ArrayList<>(), new RecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                showDeleteConfirmationDialog();
            }
        });
        adminRecyclerView.setAdapter(adminAdapter);

        adminReference = FirebaseDatabase.getInstance().getReference("Admins");

        fetchDatabaseData(2);
    }

    private void fetchDatabaseData(int userType) {
        DatabaseReference databaseReference;
        List<String> dataList;
        List<String> idsList;

        if(userType == 1) {
            databaseReference = instructorReference;
            dataList = instructorList;
            idsList = instructorIds;
        } else {
            databaseReference = adminReference;
            dataList = adminList;
            idsList = adminIds;
        }
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                dataList.clear();
                idsList.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    String userId = snapshot.getKey();
                    String userName = snapshot.child("userName").getValue(String.class);
                    dataList.add(userName);
                    idsList.add(userId);
                }

                if (userType == 1) {
                    instructorAdapter.setData(dataList);
                    instructorAdapter.setInstructorIds(idsList);
                    instructorAdapter.notifyDataSetChanged();
                } else {
                    adminAdapter.setData(dataList);
                    adminAdapter.setInstructorIds(idsList);
                    adminAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.d("FirebaseError", databaseError.getMessage());
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
                Toast.makeText(CreatedAccountsDisplayer.this, "Feature not Available Yet :)", Toast.LENGTH_SHORT).show();
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