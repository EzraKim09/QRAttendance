package com.oomproject.qrattendance;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class CreateNewClassActivity extends AppCompatActivity {

    private DatabaseReference databaseReference;
    private EditText classIdEditText;
    private EditText instructorNameEditText;
    private EditText dateEditText;
    private EditText monthEditText;
    private EditText yearEditText;
    private Button generateQrCodeButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_new_class);

        databaseReference = FirebaseDatabase.getInstance().getReference("Classes");

        classIdEditText = findViewById(R.id.classIdEditText);
        instructorNameEditText = findViewById(R.id.instructorNameEditText);
        dateEditText = findViewById(R.id.dateEditText);
        monthEditText = findViewById(R.id.monthEditText);
        yearEditText = findViewById(R.id.yearEditText);
        generateQrCodeButton = findViewById(R.id.generateQrCodeButton);

        generateQrCodeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String classId = classIdEditText.getText().toString();
                String instructorName = instructorNameEditText.getText().toString();
                String date = dateEditText.getText().toString();
                String month = monthEditText.getText().toString();
                String year = yearEditText.getText().toString();

                String classDate = date + "-" + month + "-" + year;

                if(TextUtils.isEmpty(classId) || TextUtils.isEmpty(instructorName) || TextUtils.isEmpty(date) || TextUtils.isEmpty(month) || TextUtils.isEmpty(year)) {
                    Toast.makeText(CreateNewClassActivity.this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
                } else {
                    Classes newClass = new Classes(classId, instructorName, classDate);
                    databaseReference.child(classId).setValue(newClass)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful()) {
                                        Toast.makeText(CreateNewClassActivity.this, "Class Successfully Added to Database", Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(CreateNewClassActivity.this, InstructorMenuActivity.class);
                                        startActivity(intent);
                                        finish();
                                    } else {
                                        Toast.makeText(CreateNewClassActivity.this, "Error Creating New Class", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                }
            }
        });

        ImageButton back = findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}