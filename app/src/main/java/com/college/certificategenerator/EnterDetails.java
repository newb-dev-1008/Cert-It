package com.college.certificategenerator;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
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
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Random;

public class EnterDetails extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

    // Updates:
    // 1. QR Code Generation for each certificate
    // 2. Adding options for including logos
    // 3. Checking if the logos have background; if they do, auto removal of background

    private TextInputEditText nameOrg, certificateType, nameParticipant, certificateText, name1, name2, desg1, desg2;
    private TextView issueDate, addAttesters, fileName1, fileName2;
    private ImageView delete1, delete2, calendar, addAttesterIcon;
    private MaterialButton selectButton, chooseTemplate;
    private LinearLayout linear1, linear2;
    private String namePerson, nameCompany, certType, certText, firstName, secondName, desgOne, desgTwo, date;
    private ArrayList<Bitmap> signPictures;
    private int fileNo;
    private HashMap<String, String> details;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.enter_details);

        fileNo = 1;
        details = new HashMap<>();

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
        fileName1 = findViewById(R.id.uploadSignNameTV);
        fileName2 = findViewById(R.id.uploadSignNameTV2);

        linear1 = findViewById(R.id.enterDetailsLinear1);
        linear2 = findViewById(R.id.enterDetailsLinear2);

        delete1 = findViewById(R.id.uploadSignDeleteImg);
        delete2 = findViewById(R.id.uploadSignDeleteImg2);
        calendar = findViewById(R.id.calendarIcon);
        addAttesterIcon = findViewById(R.id.addIcon);

        selectButton = findViewById(R.id.chooseSignatures);
        chooseTemplate = findViewById(R.id.chooseTemplate);

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

        chooseTemplate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chooseTemplate();
            }
        });
    }

    private void chooseTemplate() {
        namePerson = nameParticipant.getText().toString();
        nameCompany = nameOrg.getText().toString();
        certType = certificateType.getText().toString();
        // certText = certificateText.getText().toString();
        firstName = name1.getText().toString();
        secondName = name2.getText().toString();
        desgOne = desg1.getText().toString();
        desgTwo = desg2.getText().toString();
        details.put("Name", namePerson);
        details.put("Company_Name", nameCompany);
        details.put("Certificate", certType);
        details.put("Certifier1", firstName);
        details.put("Certifier_Position1", desgOne);
        details.put("Certifier2", secondName);
        details.put("Certifier_Position2", desgTwo);
        details.put("Date", date);

        Intent intent = new Intent(EnterDetails.this, ChooseTemps.class);
        // intent.putExtra("Details", details);
        // intent.putExtra("flag", 0);
        startActivity(intent);
    }

    private void addAttesters() {
        linear1.setVisibility(View.VISIBLE);
        linear2.setVisibility(View.VISIBLE);
        addAttesters.setVisibility(View.GONE);
        addAttesterIcon.setVisibility(View.GONE);

        selectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, 0);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode != RESULT_CANCELED) {
            switch (requestCode) {
                case 1:
                    if (requestCode == RESULT_OK && data != null) {
                        Uri selectedImage = data.getData();
                        String[] filePathColumn = {MediaStore.Images.Media.DATA};
                        if (selectedImage != null) {
                            Cursor cursor = getApplicationContext().getContentResolver().query(selectedImage,
                                    filePathColumn, null, null, null);
                            if (cursor != null) {
                                cursor.moveToFirst();
                                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                                String picturePath = cursor.getString(columnIndex);
                                Bitmap bitmap = BitmapFactory.decodeFile(picturePath);
                                signPictures.add(bitmap);
                                // populateRecyclerView(bitmap, picturePath);
                                if (fileNo == 1) {
                                    fileName1.setText(picturePath);
                                    fileNo++;
                                } else if (fileNo == 2) {
                                    fileName2.setText(picturePath);
                                }
                                cursor.close();

                            }
                        }
                    }
                    break;
            }
        }
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
