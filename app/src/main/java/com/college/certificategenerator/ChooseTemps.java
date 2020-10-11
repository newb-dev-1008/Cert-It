package com.college.certificategenerator;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;

public class ChooseTemps extends AppCompatActivity {

    private MaterialButton selectTemplate;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.choose_temps);

        selectTemplate = findViewById(R.id.selectTemplateButton5);

        selectTemplate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ChooseTemps.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }
}
