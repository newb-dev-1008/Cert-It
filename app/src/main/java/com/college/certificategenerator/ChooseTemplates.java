package com.college.certificategenerator;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.HashMap;

public class ChooseTemplates extends AppCompatActivity {

    private HashMap<String, String> details;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        details = (HashMap<String, String>) getIntent().getExtras().get("Details");
    }
}
