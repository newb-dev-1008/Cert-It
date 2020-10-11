package com.college.certificategenerator;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.college.certificategenerator.R;
import com.google.android.material.textfield.TextInputEditText;

public class MainActivity extends AppCompatActivity {

    private ImageView ivCertificate;
    private Button btn_changeTemplate,btn_changeInput,btn_confirm, holdCertificateButton;
    private View customAlert;
    private TextInputEditText etChangeName,etChangeAchievement;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ivCertificate = findViewById(R.id.ivCertificate);
        customAlert = findViewById(R.id.custom_alert);
        btn_changeTemplate = findViewById(R.id.btn_change_template);
        btn_changeInput = findViewById(R.id.btn_change_input);
        btn_confirm = findViewById(R.id.btn_Confirm);
        etChangeName = findViewById(R.id.etChangeName);
        etChangeAchievement = findViewById(R.id.etChangeAchievement);
        holdCertificateButton = findViewById(R.id.holdCertificateButton);

        holdCertificateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Finish this (Anshuman's stuff)
            }
        });
    }

    public void changeInput(View view)
    {
        customAlert.setVisibility(View.VISIBLE);
        Log.e("Change","Success");
    }
    public void btn_confirm(View view)
    {
        String changedName = etChangeName.getText().toString().trim();
        String changedAchievement = etChangeAchievement.getText().toString().trim();
        customAlert.setVisibility(View.GONE);
    }
}