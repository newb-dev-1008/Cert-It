package com.college.certificategenerator;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;

public class ScanPhotosActivity extends AppCompatActivity {

    private MaterialButton capturePhoto, scanPhoto;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.scan_csv);

        capturePhoto = findViewById(R.id.scanCSVCamera);
        scanPhoto = findViewById(R.id.scanGalleryPhoto);

        capturePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ScanPhotosActivity.this, CapturePhotoActivity.class);
                startActivity(intent);
            }
        });

        scanPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                startActivity(intent);
            }
        });
    }
}
