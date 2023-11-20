package com.oomproject.qrattendance;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class InstructorRegister extends AppCompatActivity {

    EditText editTextEmail, editTextPassword;
    Button buttonReg;
    FirebaseAuth mAuth;
    ProgressBar progressBar;
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instructor_register);

        mAuth = FirebaseAuth.getInstance();
        editTextEmail = findViewById(R.id.username);
        editTextPassword = findViewById(R.id.password);
        buttonReg = findViewById(R.id.btn_register);
        progressBar = findViewById(R.id.progressBar);
        textView = findViewById(R.id.loginNow);

        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplication(), InstructorLogin.class);
                startActivity(intent);
                finish();
            }
        });

        buttonReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressBar.setVisibility(View.VISIBLE);
                String email, password;
                email = String.valueOf(editTextEmail.getText());
                password = String.valueOf(editTextPassword.getText());

                if(TextUtils.isEmpty(email)) {
                    Toast.makeText(InstructorRegister.this, "Enter Username", Toast.LENGTH_SHORT).show();
                    return;
                }

                if(TextUtils.isEmpty(password)) {
                    Toast.makeText(InstructorRegister.this, "Enter Password", Toast.LENGTH_SHORT).show();
                    return;
                }

                mAuth.createUserWithEmailAndPassword(email + "@kim.in", password)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                progressBar.setVisibility(View.GONE);
                                if (task.isSuccessful()) {
                                    updateDatabase(email);
                                    Toast.makeText(InstructorRegister.this, "Account Created",
                                            Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(getApplicationContext(), InstructorMenuActivity.class);
                                    startActivity(intent);
                                    finish();
                                } else {
                                    Toast.makeText(InstructorRegister.this, "Authentication failed.",
                                            Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });
    }

    private void updateDatabase(String email) {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
        DatabaseReference instructorsReference = databaseReference.child("Instructors");
        String userId = mAuth.getCurrentUser().getUid();
        instructorsReference.child(userId).child("userName").setValue(email);
    }
}