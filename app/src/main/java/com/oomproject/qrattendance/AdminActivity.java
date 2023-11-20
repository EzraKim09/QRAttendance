package com.oomproject.qrattendance;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;
import com.google.firebase.auth.FirebaseAuth;

public class AdminActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        ImageButton back = findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        MaterialButton createNewClass = findViewById(R.id.viewAccountsButton);
        createNewClass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AdminActivity.this, CreatedAccountsDisplayer.class);
                startActivity(intent);
            }
        });

        MaterialButton viewClasses = findViewById(R.id.viewClassesButton);
        viewClasses.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AdminActivity.this, CreatedClassesDisplayer.class);
                startActivity(intent);
            }
        });

        MaterialButton logout = findViewById(R.id.logoutButton);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                Toast.makeText(getApplicationContext(), "Logout Successfully", Toast.LENGTH_SHORT).show();
                startActivity(intent);
                finish();
            }
        });
    }

}