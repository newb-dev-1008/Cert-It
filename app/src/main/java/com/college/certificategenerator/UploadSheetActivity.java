package com.college.certificategenerator;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import java.io.File;

public class UploadSheetActivity extends AppCompatActivity {

    private MaterialButton selectFileButton, uploadFileButton;
    private TextView uploadProgress, uploadFileName;
    private CardView uploadFileCardView;
    private ProgressBar progressBar;
    private String path;
    private int typeFlag;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.upload_sheet);

        uploadProgress = findViewById(R.id.uploadProgressText);
        uploadFileName = findViewById(R.id.uploadFileNameTV);

        selectFileButton = findViewById(R.id.uploadSelectFile);
        uploadFileButton = findViewById(R.id.uploadFileButton);

        progressBar = findViewById(R.id.uploadFileProgress);
        uploadFileCardView = findViewById(R.id.uploadFileCardView);

        selectFileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectFile();
            }
        });

        uploadFileCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showFile();
            }
        });
    }

    private void selectFile() {
        AlertDialog fileType = new MaterialAlertDialogBuilder(this)
                .setTitle("Select your file extension")
                .setMessage("Which file type are you looking for?")
                .setPositiveButton("CSV Files", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                        intent.setType("text/csv");
                        startActivityForResult(intent, 1);
                    }
                }).setNegativeButton("Excel Files", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                        intent.setType("application/vnd.ms-excel");
                        startActivityForResult(intent, 2);
                    }
                }).create();

        fileType.show();
        fileType.setCanceledOnTouchOutside(false);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case 1:
                if (resultCode == RESULT_OK) {
                    typeFlag = 1;
                    path = data.getData().getPath();
                    String[] s = path.split("/");
                    String fileName = s[(s.length - 1)];
                    uploadFileCardView.setVisibility(View.VISIBLE);
                    uploadProgress.setVisibility(View.VISIBLE);
                    progressBar.setVisibility(View.VISIBLE);
                    uploadFileButton.setVisibility(View.VISIBLE);
                    selectFileButton.setText("Change selected file");
                    uploadFileName.setText(fileName);
                }
            case 2:
                if (resultCode == RESULT_OK) {
                    typeFlag = 2;
                    path = data.getData().getPath();
                    String[] s = path.split("/");
                    String fileName = s[(s.length - 1)];
                    uploadFileCardView.setVisibility(View.VISIBLE);
                    uploadProgress.setVisibility(View.VISIBLE);
                    progressBar.setVisibility(View.VISIBLE);
                    uploadFileButton.setVisibility(View.VISIBLE);
                    selectFileButton.setText("Change selected file");
                    uploadFileName.setText(fileName);
                }
        }
    }

    private void showFile() {
        File file = new File(path);
        if (file.exists()) {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            Uri uri = Uri.fromFile(file);
            intent.setDataAndType(uri, "")
        } else {

        }
    }
}
