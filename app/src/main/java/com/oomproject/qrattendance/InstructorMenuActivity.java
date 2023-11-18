package com.oomproject.qrattendance;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;

public class InstructorMenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instructor_menu);

        ImageButton back = findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        MaterialButton createNewClass = findViewById(R.id.createNewClassButton);
        createNewClass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(InstructorMenuActivity.this, CreateNewClassActivity.class);
                startActivity(intent);
            }
        });

        MaterialButton viewClasses = findViewById(R.id.viewClassesButton);
        viewClasses.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(InstructorMenuActivity.this, PastClassesDisplayer.class);
                startActivity(intent);
            }
        });
    }
}