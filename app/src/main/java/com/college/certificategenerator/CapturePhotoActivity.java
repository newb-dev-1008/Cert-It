package com.college.certificategenerator;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.camera.core.Camera;
import androidx.camera.core.CameraSelector;
import androidx.camera.core.ExperimentalGetImage;
import androidx.camera.core.ImageAnalysis;
import androidx.camera.core.ImageCapture;
import androidx.camera.core.ImageCaptureException;
import androidx.camera.core.ImageProxy;
import androidx.camera.core.Preview;
import androidx.camera.extensions.HdrImageCaptureExtender;
import androidx.camera.lifecycle.ProcessCameraProvider;
import androidx.camera.view.PreviewView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.mlkit.vision.common.InputImage;
import com.google.mlkit.vision.text.Text;
import com.google.mlkit.vision.text.TextRecognition;
import com.google.mlkit.vision.text.TextRecognizer;

import java.nio.ByteBuffer;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class CapturePhotoActivity extends AppCompatActivity {

    private int REQUEST_CODE_PERMISSIONS = 1001;
    private final String[] REQUIRED_PERMISSIONS = new String[]{"android.permission.CAMERA", "android.permission.WRITE_EXTERNAL_STORAGE"};
    private Executor executor = Executors.newSingleThreadExecutor();

    private PreviewView cameraView;
    private ImageView captureButton, flash_on, flash_off, flash_auto, capturedImageView;
    private MaterialButton cancelCapture, submitCapture, retryCapture;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.capture_image);

        cameraView = findViewById(R.id.camera);
        capturedImageView = findViewById(R.id.capturedImage);
        captureButton = findViewById(R.id.captureImg);
        flash_on = findViewById(R.id.flash_on);
        flash_off = findViewById(R.id.flash_off);
        flash_auto = findViewById(R.id.flash_auto);
        cancelCapture = findViewById(R.id.cancelCapturedVerification);
        retryCapture = findViewById(R.id.retryCapturedVerification);
        submitCapture = findViewById(R.id.submitCapturedVerification);

        if (allPermissionsGranted()) {
            startCamera();
        } else {
            ActivityCompat.requestPermissions(this, REQUIRED_PERMISSIONS, REQUEST_CODE_PERMISSIONS);
        }
    }

    private boolean allPermissionsGranted(){
        for(String permission : REQUIRED_PERMISSIONS){
            if(ContextCompat.checkSelfPermission(CapturePhotoActivity.this, permission) != PackageManager.PERMISSION_GRANTED){
                return false;
            }
        }
        return true;
    }

    private void startCamera() {
        final ListenableFuture<ProcessCameraProvider> cameraProviderFuture = ProcessCameraProvider.getInstance(this);

        cameraProviderFuture.addListener(new Runnable() {
            @Override
            public void run() {
                try {
                    ProcessCameraProvider cameraProvider = cameraProviderFuture.get();
                    bindPreview(cameraProvider);
                } catch (ExecutionException | InterruptedException e) {
                    // No errors need to be handled for this Future.
                    // This should never be reached.
                    Toast.makeText(CapturePhotoActivity.this, "Can't find a camera for preview.", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(CapturePhotoActivity.this, ScanPhotosActivity.class);
                    finish();
                    startActivity(intent);
                }
            }
        }, ContextCompat.getMainExecutor(this));
    }

    void bindPreview(@NonNull ProcessCameraProvider cameraProvider) {
        Preview preview = new Preview.Builder()
                .build();

        CameraSelector cameraSelector = new CameraSelector.Builder()
                .requireLensFacing(CameraSelector.LENS_FACING_BACK)
                .build();

        ImageAnalysis imageAnalysis = new ImageAnalysis.Builder()
                .build();

        ImageCapture.Builder builder = new ImageCapture.Builder();

        //Vendor-Extensions (The CameraX extensions dependency in build.gradle)
        HdrImageCaptureExtender hdrImageCaptureExtender = HdrImageCaptureExtender.create(builder);

        // Query if extension is available (optional).
        if (hdrImageCaptureExtender.isExtensionAvailable(cameraSelector)) {
            // Enable the extension if available.
            hdrImageCaptureExtender.enableExtension(cameraSelector);
        }

        final ImageCapture imageCapture = builder
                .setTargetRotation(this.getWindowManager().getDefaultDisplay().getRotation())
                .setFlashMode(ImageCapture.FLASH_MODE_OFF)
                .setCaptureMode(ImageCapture.CAPTURE_MODE_MAXIMIZE_QUALITY)
                .build();
        preview.setSurfaceProvider(cameraView.createSurfaceProvider());
        Camera camera = cameraProvider.bindToLifecycle(this, cameraSelector, preview, imageAnalysis, imageCapture);

        retryCapture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cancelCapture.setVisibility(View.GONE);
                retryCapture.setVisibility(View.GONE);
                submitCapture.setVisibility(View.GONE);
                capturedImageView.setVisibility(View.GONE);

                cameraView.setVisibility(View.VISIBLE);
                captureButton.setVisibility(View.VISIBLE);
                startCamera();
            }
        });

        flash_off.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                flash_off.setVisibility(View.GONE);
                flash_on.setVisibility(View.VISIBLE);

                imageCapture.setFlashMode(ImageCapture.FLASH_MODE_ON);
                Toast.makeText(CapturePhotoActivity.this, "Flash: ON", Toast.LENGTH_SHORT).show();
                // setFlash(1);
            }
        });

        flash_on.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                flash_on.setVisibility(View.GONE);
                flash_auto.setVisibility(View.VISIBLE);

                imageCapture.setFlashMode(ImageCapture.FLASH_MODE_AUTO);
                Toast.makeText(CapturePhotoActivity.this, "Flash: AUTO", Toast.LENGTH_SHORT).show();
                // setFlash(0);
            }
        });

        flash_auto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                flash_auto.setVisibility(View.GONE);
                flash_off.setVisibility(View.VISIBLE);

                imageCapture.setFlashMode(ImageCapture.FLASH_MODE_OFF);
                Toast.makeText(CapturePhotoActivity.this, "Flash: OFF", Toast.LENGTH_SHORT).show();
                // setFlash(2);
            }
        });

        captureButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageCapture.takePicture(executor, new ImageCapture.OnImageCapturedCallback() {
                    @Override
                    public void onCaptureSuccess(@NonNull final ImageProxy image) {
                        // super.onCaptureSuccess(image);
                        runOnUiThread(new Runnable() {
                            @Override
                            @ExperimentalGetImage
                            public void run() {
                                cameraView.setVisibility(View.GONE);
                                captureButton.setVisibility(View.GONE);
                                flash_auto.setVisibility(View.GONE);
                                flash_off.setVisibility(View.GONE);
                                flash_on.setVisibility(View.GONE);

                                capturedImageView.setVisibility(View.VISIBLE);
                                cancelCapture.setVisibility(View.VISIBLE);
                                retryCapture.setVisibility(View.VISIBLE);
                                submitCapture.setVisibility(View.VISIBLE);

                                Image previewImage = image.getImage();
                                ByteBuffer previewImageBuffer = previewImage.getPlanes()[0].getBuffer();
                                byte[] previewImageBytes = new byte[previewImageBuffer.capacity()];
                                previewImageBuffer.get(previewImageBytes);
                                Bitmap previewImageBitmap = BitmapFactory.decodeByteArray(previewImageBytes, 0, previewImageBytes.length, null);
                                capturedImageView.setImageBitmap(previewImageBitmap);
                            }
                        });

                        submitCapture.setOnClickListener(new View.OnClickListener() {
                            @Override
                            @ExperimentalGetImage
                            public void onClick(View view) {
                                // Finish this
                                Image mediaImage = image.getImage();
                                if (mediaImage != null){
                                    InputImage inputImage = InputImage.fromMediaImage(mediaImage, image.getImageInfo().getRotationDegrees());
                                    verifyID(inputImage, image);
                                    // Still some work left (Handling images from gallery, defining a function to automatically find name)
                                }
                            }
                        });
                    }

                    @Override
                    public void onError(@NonNull ImageCaptureException exception) {
                        AlertDialog captureFailed = new MaterialAlertDialogBuilder(CapturePhotoActivity.this)
                                .setTitle("Unable to capture photos")
                                .setMessage("There seems to be a problem with capturing the photo:\n" + exception.getMessage())
                                .setPositiveButton("Retry", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        startCamera();
                                    }
                                })
                                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        Intent intent = new Intent(CapturePhotoActivity.this, ScanPhotosActivity.class);
                                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                        startActivity(intent);
                                    }
                                })
                                .create();
                        captureFailed.show();
                        captureFailed.setCanceledOnTouchOutside(false);
                        captureFailed.setCancelable(false);
                    }
                });

            }
        });
    }

    private void test_text(Text textTask){
        String textResult = textTask.getText();
        Intent intent = new Intent(CapturePhotoActivity.this, ShowScannedText.class);
        intent.putExtra("test_text", textResult);
        startActivity(intent);

        // Needs completion
    }

    private void verifyID(InputImage IDImage, final ImageProxy proxyImage){
        TextRecognizer recognizer = TextRecognition.getClient();

        Task<Text> result = recognizer.process(IDImage).addOnSuccessListener(new OnSuccessListener<Text>() {
            @Override
            public void onSuccess(final Text text) {
                AlertDialog textRegistered = new MaterialAlertDialogBuilder(CapturePhotoActivity.this)
                        .setTitle("Photo captured.")
                        .setMessage("We will be extracting text out of your image and returning with the generated certificates.")
                        .setPositiveButton("Okay", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                proxyImage.close();
                                /*Intent intent = new Intent(CaptureImageActivity.this, StudentUserProfile.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent);*/

                                // To be deleted
                                test_text(text);
                            }
                        })
                        .create();
                textRegistered.show();
                textRegistered.setCanceledOnTouchOutside(false);
                textRegistered.setCancelable(false);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                AlertDialog textRegisteredFailed = new MaterialAlertDialogBuilder(CapturePhotoActivity.this)
                        .setTitle("Failed to convert your image to CSV")
                        .setMessage(e.getMessage() + "\nYour image seems to be out of focus and not clear enough. Please retry with a clearer picture.")
                        .setPositiveButton("Retry", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                proxyImage.close();
                                cameraView.setVisibility(View.VISIBLE);
                                captureButton.setVisibility(View.VISIBLE);

                                capturedImageView.setVisibility(View.GONE);
                                cancelCapture.setVisibility(View.GONE);
                                retryCapture.setVisibility(View.GONE);
                                submitCapture.setVisibility(View.GONE);
                                startCamera();
                            }
                        })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                proxyImage.close();
                                Toast.makeText(CapturePhotoActivity.this, "Cancelled Scanning.", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(CapturePhotoActivity.this, ScanPhotosActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent);
                            }
                        })
                        .create();
                textRegisteredFailed.show();
                textRegisteredFailed.setCancelable(false);
                textRegisteredFailed.setCanceledOnTouchOutside(false);
            }
        });
    }
}
