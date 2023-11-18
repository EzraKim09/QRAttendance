package com.oomproject.qrattendance;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;

import java.io.ByteArrayOutputStream;

public class CreateNewClassActivity extends AppCompatActivity {

    private DatabaseReference databaseReference;
    private EditText classIdEditText;
    private EditText instructorNameEditText;
    private EditText dateEditText;
    private EditText monthEditText;
    private EditText yearEditText;
    private Button generateQrCodeButton;
    private ImageView imageView;

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
        imageView = findViewById(R.id.classQrImage);

        generateQrCodeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String classId = classIdEditText.getText().toString();
                String instructorName = instructorNameEditText.getText().toString();
                String date = dateEditText.getText().toString();
                String month = monthEditText.getText().toString();
                String year = yearEditText.getText().toString();
                long timestamp = System.currentTimeMillis();

                String classDate = date + "-" + month + "-" + year;

                if(TextUtils.isEmpty(classId) || TextUtils.isEmpty(instructorName) || TextUtils.isEmpty(date) || TextUtils.isEmpty(month) || TextUtils.isEmpty(year)) {
                    Toast.makeText(CreateNewClassActivity.this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
                } else {
                    MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
                    try {
                        BitMatrix bitMatrix = multiFormatWriter.encode(classId, BarcodeFormat.QR_CODE, 300, 300);
                        BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
                        Bitmap bitmap = barcodeEncoder.createBitmap(bitMatrix);

                        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                        bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
                        byte[] byteArray = byteArrayOutputStream.toByteArray();
                        String classQrCode = Base64.encodeToString(byteArray, Base64.DEFAULT);

                        imageView.setImageBitmap(bitmap);

                        Classes newClass = new Classes(classId, instructorName, classDate, classQrCode, timestamp);
                        databaseReference.child(classId).setValue(newClass)
                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if(task.isSuccessful()) {
                                            Toast.makeText(CreateNewClassActivity.this, "Class Successfully Added to Database", Toast.LENGTH_SHORT).show();
                                            Intent intent = new Intent(CreateNewClassActivity.this, ShowQrCodeActivity.class);
                                            intent.putExtra("classQrCode", classQrCode);
                                            startActivity(intent);
                                            finish();
                                        } else {
                                            Toast.makeText(CreateNewClassActivity.this, "Error Creating New Class", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });
                    } catch (WriterException e) {
                        Toast.makeText(CreateNewClassActivity.this, "Error Creating New Class", Toast.LENGTH_SHORT).show();
                        e.printStackTrace();
                    }
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