package com.college.certificategenerator;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.mlkit.vision.common.InputImage;
import com.google.mlkit.vision.text.Text;
import com.google.mlkit.vision.text.TextRecognition;
import com.google.mlkit.vision.text.TextRecognizer;

public class ScanPhotosActivity extends AppCompatActivity {

    private MaterialButton capturePhoto, scanPhoto;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.scan_csv);

        capturePhoto = findViewById(R.id.scanCSVCamera);
        scanPhoto = findViewById(R.id.scanGalleryPhoto);

        capturePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ScanPhotosActivity.this, CapturePhotoActivity.class);
                startActivity(intent);
            }
        });

        scanPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent pickPhoto = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(pickPhoto, 0);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_CANCELED) {
            switch (requestCode) {
                case 0:
                    if (resultCode == RESULT_OK && data != null) {
                        Uri selectedImage = data.getData();
                        String[] filePathColumn = {MediaStore.Images.Media.DATA};
                        if (selectedImage != null) {
                            Cursor cursor = getContentResolver().query(selectedImage,
                                    filePathColumn, null, null, null);
                            if (cursor != null) {
                                cursor.moveToFirst();

                                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                                String picturePath = cursor.getString(columnIndex);
                                // idImage.setImageBitmap(BitmapFactory.decodeFile(picturePath));
                                Bitmap bitmap = BitmapFactory.decodeFile(picturePath);
                                verifyID(bitmap);
                                cursor.close();

                            }
                        }
                    }
                    break;
            }
        }
    }

    private void verifyID(Bitmap bitmapID){
        TextRecognizer recognizer = TextRecognition.getClient();

        int rotationDegree = 0;
        InputImage image = InputImage.fromBitmap(bitmapID, rotationDegree);

        Task<Text> result =
                recognizer.process(image).addOnSuccessListener(new OnSuccessListener<Text>() {
                    @Override
                    public void onSuccess(final Text visionText) {
                        AlertDialog textRegistered = new MaterialAlertDialogBuilder(ScanPhotosActivity.this)
                                .setTitle("Photo Parsed")
                                .setMessage("We have recognized the text from your given photo and will be returning the certificate shortly.")
                                .setPositiveButton("Okay", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        test_text(visionText);      // To be deleted (and replace DialogInterface.OnClickListener with null)
                                    }
                                })
                                .create();
                        textRegistered.show();
                        textRegistered.setCanceledOnTouchOutside(false);
                        textRegistered.setCancelable(false);
                    }
                })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                AlertDialog textRegisteredFailed = new MaterialAlertDialogBuilder(ScanPhotosActivity.this)
                                        .setTitle("Failed to register your ID")
                                        .setMessage(e.getMessage() + "\nPlease retry.")
                                        .setPositiveButton("Retry", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialogInterface, int i) {
                                                Intent intent = new Intent(ScanPhotosActivity.this, ScanPhotosActivity.class);
                                                startActivity(intent);
                                            }
                                        })
                                        .setNegativeButton("Cancel Verification", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialogInterface, int i) {
                                                Toast.makeText(ScanPhotosActivity.this, "Cancelled text recognition.", Toast.LENGTH_SHORT).show();
                                            }
                                        })
                                        .create();
                                textRegisteredFailed.show();
                                textRegisteredFailed.setCancelable(false);
                                textRegisteredFailed.setCanceledOnTouchOutside(false);
                            }
                        });
    }

    // To be deleted
    private void test_text(Text textTask){
        String textResult = textTask.getText();
        Intent intent = new Intent(ScanPhotosActivity.this, ShowScannedText.class);
        intent.putExtra("test_text", textResult);
        startActivity(intent);
    }
}
