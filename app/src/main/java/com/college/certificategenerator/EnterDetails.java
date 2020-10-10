package com.college.certificategenerator;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

import java.text.DateFormat;
import java.util.Calendar;

public class EnterDetails extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

    // Updates:
    // 1. QR Code Generation for each certificate
    // 2. Adding options for including logos
    // 3. Checking if the logos have background; if they do, auto removal of background

    private TextInputEditText nameOrg, certificateType, nameParticipant, certificateText, name1, name2, desg1, desg2;
    private TextView issueDate, addAttesters;
    private ImageView delete1, delete2, calendar, addAttesterIcon;
    private MaterialButton selectButton;
    private String namePerson, nameCompany, certType, certText, firstName, secondName, desgOne, desgTwo, date;

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
        certificateText = findViewById(R.id.entry_name_certificateTextET);

        issueDate = findViewById(R.id.entry_dateET);
        addAttesters = findViewById(R.id.uploadSignTV);

        delete1 = findViewById(R.id.uploadSignDeleteImg);
        delete2 = findViewById(R.id.uploadSignDeleteImg2);
        calendar = findViewById(R.id.calendarIcon);
        addAttesterIcon = findViewById(R.id.addIcon);

        selectButton = findViewById(R.id.chooseSignatures);

        issueDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                returnDate();
            }
        });
    }

    private void returnDate() {
        DialogFragment datePicker = new DatePickerFragment();
        datePicker.show(getSupportFragmentManager(), "date picker");
    }

    @Override
    public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, month);
        c.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        String currentDateString = DateFormat.getDateInstance(DateFormat.FULL).format(c.getTime());
        TextView textView = findViewById(R.id.textView);
        textView.setText(currentDateString);
    }

    private void generateID() {

    }
}
