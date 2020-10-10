package com.college.certificategenerator;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class EnterDetails extends AppCompatActivity {

    // Updates:
    // 1. QR Code Generation for each certificate
    // 2. Adding options for including logos
    // 3. Checking if the logos have background; if they do, auto removal of background

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.enter_details);
    }
}
