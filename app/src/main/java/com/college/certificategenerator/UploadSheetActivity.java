package com.college.certificategenerator;

import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.util.HashMap;

public class UploadSheetActivity extends AppCompatActivity {

    private MaterialButton selectFileButton, selectTemplateButton;
    private TextView uploadProgress, uploadFileName;
    private CardView uploadFileCardView;
    private ProgressBar progressBar;
    private ImageView deleteIcon;
    private String path;
    private int typeFlag;
    private File file;
    private StorageReference storageReference;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.upload_sheet);

        // file = new File(null);

        uploadProgress = findViewById(R.id.uploadProgressText);
        uploadFileName = findViewById(R.id.uploadFileNameTV);

        selectFileButton = findViewById(R.id.uploadSelectFile);
        selectTemplateButton = findViewById(R.id.selectTemplateButton1);

        progressBar = findViewById(R.id.uploadFileProgress);
        uploadFileCardView = findViewById(R.id.uploadFileCardView);

        storageReference = FirebaseStorage.getInstance().getReference();

        deleteIcon = findViewById(R.id.uploadFileDeleteImg);

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

        deleteIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteSelectedFile();
            }
        });

        selectTemplateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Pratyush's Part:
                // 1. Upload the file selected using the API
                // 2. Ensure that there's some unique ID linked to the upload, because in the next step
                //    when template is selected, the template's URL must be passed linking to this CSV file's upload

                Intent intent = new Intent(UploadSheetActivity.this, ChooseTemplates.class);
                HashMap<String, String> details = new HashMap<>();
                // intent.putExtra("File", file);
                intent.putExtra("flag", 1);
                startActivity(intent);
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
                        typeFlag = 1;
                        intent.setType("text/csv");
                        startActivityForResult(intent, 0);
                    }
                }).setNegativeButton("Excel Files", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                        typeFlag = 2;
                        intent.setType("application/vnd.ms-excel");
                        startActivityForResult(intent, 0);
                    }
                }).create();

        fileType.show();
        fileType.setCanceledOnTouchOutside(false);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case 0:
                if (resultCode == RESULT_OK) {
                    path = data.getData().getPath();
                    file = new File(path);
                    String[] s = path.split("/");
                    String fileName = s[(s.length - 1)];
                    uploadFileCardView.setVisibility(View.VISIBLE);
                    // uploadProgress.setVisibility(View.VISIBLE);
                    // progressBar.setVisibility(View.VISIBLE);
                    selectTemplateButton.setVisibility(View.VISIBLE);
                    selectFileButton.setText("Change selected file");
                    uploadFileName.setText(fileName);
                }
                break;
        }
    }

    private void showFile() {
        file = new File(path);
        if (file.exists()) {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            Uri uri = Uri.fromFile(file);
            if (typeFlag == 1) {
                intent.setDataAndType(uri, "text/csv");
                intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            } else {
                intent.setDataAndType(uri, "application/vnd.ms-excel");
                intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            }
            try {
                startActivity(intent);
            } catch (ActivityNotFoundException e) {
                Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "The file does not exist. It has probably been deleted/ moved.", Toast.LENGTH_SHORT).show();
        }
    }

    private void deleteSelectedFile() {
        AlertDialog deleteFile = new MaterialAlertDialogBuilder(this)
                .setTitle("Remove selected file?")
                .setMessage("The selected file hasn't been uploaded. Are you sure you want to remove the file?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        uploadFileCardView.setVisibility(View.GONE);
                        selectTemplateButton.setVisibility(View.GONE);
                        selectFileButton.setText("Select your file");
                        String s1 = "File removed.";
                        Toast.makeText(UploadSheetActivity.this, s1, Toast.LENGTH_SHORT).show();
                    }
                }).setNegativeButton("No", null)
                .create();
        deleteFile.show();
    }
}
