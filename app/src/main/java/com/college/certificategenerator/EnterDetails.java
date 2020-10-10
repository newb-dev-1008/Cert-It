package com.college.certificategenerator;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

public class EnterDetails extends AppCompatActivity {

    // Updates:
    // 1. QR Code Generation for each certificate
    // 2. Adding options for including logos
    // 3. Checking if the logos have background; if they do, auto removal of background

    private TextInputEditText nameOrg, certificateType, nameParticipant, certificateText, name1, name2, desg1, desg2;
    private TextView issueDate, addAttesters;
    private ImageView delete1, delete2, calendar, addAttestorIcon;
    private MaterialButton selectButton;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.enter_details);

        nameOrg = findViewById(R.id.nameET);
        certificateType = findViewById(R.id.certificateTypeET);
        nameParticipant = findViewById(R.id.participantNameET);
        name1 = findViewById(R.id.authName);
        name2 = findViewById(R.id.authName2);
        desg1 = findViewById(R.id.authDesg1);
        desg2 = findViewById(R.id.authDesg2);


    }
}
