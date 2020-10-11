package com.college.certificategenerator;

import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class ShowScannedText extends AppCompatActivity {

    private TextView scannedText;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.show_scanned_text);

        scannedText = findViewById(R.id.recognizedTextTV);
        String s = getIntent().getExtras().getString("test_text");
        scannedText.setText(s);
    }
}
