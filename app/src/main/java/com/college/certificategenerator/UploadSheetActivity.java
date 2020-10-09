package com.college.certificategenerator;

import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;

public class UploadSheetActivity extends AppCompatActivity {

    private MaterialButton selectFileButton, uploadFileButton;
    private TextView uploadProgress, uploadFileName;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.upload_sheet);

        uploadProgress = findViewById(R.id.uploadProgressText);
        uploadFileName = findViewById(R.id.uploadFileNameTV);

        selectFileButton = findViewById(R.id.uploadSelectFile);
        uploadFileButton = findViewById(R.id.uploadFileButton);
    }
}
