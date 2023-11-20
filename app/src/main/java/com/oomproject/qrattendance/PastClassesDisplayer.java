package com.oomproject.qrattendance;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
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
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.journeyapps.barcodescanner.BarcodeEncoder;

import java.util.ArrayList;
import java.util.List;

public class PastClassesDisplayer extends AppCompatActivity {

    private Spinner spinnerClasses;
    private DatabaseReference classesReference;
    private DatabaseReference attendanceReference;
    private RecyclerView recyclerView;
    private RecyclerViewAdapter recyclerViewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_past_class_displayer);

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

        spinnerClasses = findViewById(R.id.spinnerClasses);
        classesReference = FirebaseDatabase.getInstance().getReference().child("Classes");
        attendanceReference = FirebaseDatabase.getInstance().getReference().child("Attendance");
        fetchClassesFromFirebase();
        setupSpinnerListener();
    }

    private void setupSpinnerListener() {
        spinnerClasses.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, android.view.View view, int position, long id) {
                String selectedClassId = spinnerClasses.getSelectedItem().toString();
                attendanceReference.child(selectedClassId).orderByChild("timeStamp").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        List<String> itemValues = new ArrayList<>();
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            String rollNo = snapshot.getKey();
                            itemValues.add(rollNo);
                        }
                        recyclerViewAdapter.setData(itemValues);
                        recyclerViewAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        Log.d("FirebaseError", databaseError.getMessage());
                    }
                });

                classesReference.child(selectedClassId).orderByChild("timeStamp").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        String instructorName = dataSnapshot.child("instructorName").getValue(String.class);
                        String classDate = dataSnapshot.child("classDate").getValue(String.class);
                        String qrCode = dataSnapshot.child("classId").getValue(String.class);

                        updateTextViews(instructorName, classDate, qrCode);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Toast.makeText(PastClassesDisplayer.this, "Select Class from the Scroll Bar", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void updateTextViews(String instructorName, String classDate, String qrCode) {
        TextView instructorNameDisplay = findViewById(R.id.instructorNameDisplay);
        TextView classDateDisplay = findViewById(R.id.classDateDisplay);
        ImageView classQrCodeDisplay = findViewById(R.id.classQrCodeDisplay);

        instructorNameDisplay.setText("Instructor: " + instructorName);
        classDateDisplay.setText("Date: " + classDate);

        Bitmap qrCodeBitmap = generateQrCode(qrCode);

        classQrCodeDisplay.setImageBitmap(qrCodeBitmap);
    }

    private Bitmap generateQrCode(String qrCodeString) {
        MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
        try {
            com.google.zxing.common.BitMatrix bitMatrix = multiFormatWriter.encode(qrCodeString, BarcodeFormat.QR_CODE, 600, 600);
            BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
            return barcodeEncoder.createBitmap(bitMatrix);
        } catch (WriterException e) {
            e.printStackTrace();
            return null;
        }
    }


    private void fetchClassesFromFirebase() {
        classesReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                List<String> classIDsList = new ArrayList<>();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    String classId = snapshot.child("classId").getValue(String.class);
                    if (classId != null) {
                        classIDsList.add(classId);
                    }
                }

                ArrayAdapter<String> adapter = new ArrayAdapter<>(PastClassesDisplayer.this,
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