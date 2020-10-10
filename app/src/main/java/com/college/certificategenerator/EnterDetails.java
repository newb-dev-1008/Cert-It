package com.college.certificategenerator;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.Random;

public class EnterDetails extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

    // Updates:
    // 1. QR Code Generation for each certificate
    // 2. Adding options for including logos
    // 3. Checking if the logos have background; if they do, auto removal of background

    private TextInputEditText nameOrg, certificateType, nameParticipant, certificateText, name1, name2, desg1, desg2;
    private TextView issueDate, addAttesters;
    private ImageView delete1, delete2, calendar, addAttesterIcon;
    private MaterialButton selectButton;
    private LinearLayout linear1, linear2;
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

        calendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                returnDate();
            }
        });

        addAttesters.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addAttesters();
            }
        });

        addAttesterIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addAttesters();
            }
        });
    }

    private void addAttesters() {
        linear1.setVisibility(View.VISIBLE);
        linear2.setVisibility(View.VISIBLE);
        addAttesters.setVisibility(View.GONE);
        addAttesterIcon.setVisibility(View.GONE);
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

        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH);
        // String currentDateString = DateFormat.getDateInstance(DateFormat.FULL).format(c.getTime());
        String currentDateString = format.format(c.getTime());

        issueDate.setText(currentDateString);
    }

    private String generateProjectID() {
        StringBuilder sb = new StringBuilder(7);
        String DATA = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        Random RANDOM = new Random();
        for (int i = 0; i < 7; i++) {
            sb.append(DATA.charAt(RANDOM.nextInt(DATA.length())));
        }

        return sb.toString();
    }
}
