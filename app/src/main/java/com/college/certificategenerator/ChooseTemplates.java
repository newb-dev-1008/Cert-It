package com.college.certificategenerator;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.storage.StorageReference;

import java.util.HashMap;

public class ChooseTemplates extends AppCompatActivity {

    private HashMap<String, String> details;
    private StorageReference storageReference;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.choose_templates);
        details = new HashMap<>();
        details = (HashMap<String, String>) getIntent().getExtras().get("Details");
    }
}
