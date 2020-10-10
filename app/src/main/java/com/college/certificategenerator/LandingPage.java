package com.college.certificategenerator;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;

public class LandingPage extends AppCompatActivity {

    // Updates
    // 1. Adding voice assistant

    private MaterialButton generateOne, generateMany;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.certificate_entry);

        generateMany = findViewById(R.id.generateFromCSV);
        generateOne = findViewById(R.id.generateOne);

        generateOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LandingPage.this, EnterDetails.class);
                startActivity(intent);
            }
        });

        generateMany.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                generateMany();
            }
        });
    }

    private void generateMany() {
        Intent intent = new Intent(LandingPage.this, UploadSheetActivity.class);
        startActivity(intent);
    }

}
