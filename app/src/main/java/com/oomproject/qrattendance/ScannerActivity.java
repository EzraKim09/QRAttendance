package com.oomproject.qrattendance;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.budiyev.android.codescanner.CodeScanner;
import com.budiyev.android.codescanner.CodeScannerView;
import com.budiyev.android.codescanner.DecodeCallback;
import com.google.android.material.button.MaterialButton;
import com.google.zxing.Result;

import android.Manifest;

import org.jetbrains.annotations.NonNls;

import java.lang.annotation.Native;

public class ScannerActivity extends AppCompatActivity {

    CodeScanner codeScanner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scanner);

        permissionCheck();
        CodeScannerView codeScannerView = findViewById(R.id.scannerView);
        codeScanner = new CodeScanner(this, codeScannerView);
        codeScanner.setDecodeCallback(new DecodeCallback() {
            @Override
            public void onDecoded(@NonNull Result result) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        String qrCodeContent = result.getText();
                        if (isUrl(qrCodeContent)) {
                            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(qrCodeContent));
                            startActivity(intent);
                        } else {
                            Toast.makeText(ScannerActivity.this, "Invalid QR Code", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });

        codeScannerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                codeScanner.startPreview();
            }
        });

        ImageButton back = findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        MaterialButton viewAttendance = findViewById(R.id.viewAttendanceButton);
        viewAttendance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ScannerActivity.this, ComingSoon.class);
                startActivity(intent);
            }
        });
    }

    private void permissionCheck() {
        if(checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, 1);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode != 1) {
            permissionCheck();
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    protected void onResume() {
        super.onResume();
        codeScanner.startPreview();
    }

    @Override
    protected void onPause() {
        super.onPause();
        codeScanner.stopPreview();
    }

    private boolean isUrl(String text) {
        // I may need to change this so that the scanner can only scan the QR code of classes
        return text.startsWith("http://") || text.startsWith("https://");
    }
}