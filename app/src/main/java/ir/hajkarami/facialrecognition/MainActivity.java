
package ir.hajkarami.facialrecognition;

import android.app.ComponentCaller;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.mlkit.vision.common.InputImage;
import com.google.mlkit.vision.face.Face;
import com.google.mlkit.vision.face.FaceContour;
import com.google.mlkit.vision.face.FaceDetection;
import com.google.mlkit.vision.face.FaceDetector;
import com.google.mlkit.vision.face.FaceDetectorOptions;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    ImageView imageView;
    Button btn_dialog;
    TextView textView;
    private final static int RequestCode = 123;
    InputImage firebaseVision;
    FaceDetector faceDetector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        imageView = findViewById(R.id.imageView);
        btn_dialog = findViewById(R.id.btn_camera);
        textView = findViewById(R.id.textView);
        FirebaseApp.initializeApp(this);

        btn_dialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OpenFile();
            }
        });
        Toast.makeText(this, "App is Stated", Toast.LENGTH_SHORT).show();
    }

    private void OpenFile() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(intent, RequestCode);
        } else
            Toast.makeText(this, "Filed", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data, @NonNull ComponentCaller caller) {
        super.onActivityResult(requestCode, resultCode, data, caller);
        Bundle bundle = data.getExtras();
        Bitmap bitmap = (Bitmap) bundle.get("data");
        FaceDetectionProcess(bitmap);
        Toast.makeText(this, "Success!!", Toast.LENGTH_SHORT).show();
    }

    private void FaceDetectionProcess(Bitmap bitmap) {
        textView.setText("Processing Image...");
        final StringBuilder builder = new StringBuilder();
        BitmapDrawable drawable = (BitmapDrawable) imageView.getDrawable();
        InputImage inputImage = InputImage.fromBitmap(bitmap, 0);
        FaceDetectorOptions faceDetectorOptions = new FaceDetectorOptions.Builder()
                .setPerformanceMode(FaceDetectorOptions.PERFORMANCE_MODE_ACCURATE)
                .setLandmarkMode(FaceDetectorOptions.LANDMARK_MODE_ALL)
                .setClassificationMode(FaceDetectorOptions.CLASSIFICATION_MODE_ALL)
                .enableTracking().build();

        // Real-time contour detection
        FaceDetectorOptions realTimeOpts =
                new FaceDetectorOptions.Builder()
                        .setContourMode(FaceDetectorOptions.CONTOUR_MODE_ALL)
                        .build();
//        class YourAnalyzer implements ImageAnalysis.Analyzer {
//
//            @OptIn(markerClass = ExperimentalGetImage.class)
//            @Override
//            public void analyze(ImageProxy imageProxy) {
//                Image mediaImage = imageProxy.getImage();
//                if (mediaImage != null) {
//                    InputImage image =
//                            InputImage.fromMediaImage(mediaImage, imageProxy.getImageInfo().getRotationDegrees());
//                    // Pass image to an ML Kit Vision API
//                    // ...
//                }
//            }
//        }
        FaceDetector detector = FaceDetection.getClient(faceDetectorOptions);
        Task<List<Face>> result = detector.process(inputImage)
                .addOnSuccessListener(faces -> {
                    if (faces.size() != 0) {
                        if (faces.size() == 1) {
                            builder.append(faces.size() + "Face Detected \n\n");

                        } else if (faces.size() > 1) {
                            builder.append(faces.size() + "Face Detected \n\n");
                        }
                        for (Face face : faces) {
                            int id = face.getTrackingId();
                            float rotY = face.getHeadEulerAngleY();
                            float rotZ = face.getHeadEulerAngleZ();
                            builder.append("1. Face Traking ID [" + id + "]\n");
                            builder.append("2. Head Rotation to Right [" + String.format("%.2f", rotY) + " deg. ]\n");
                            builder.append("3. Head Title Sideways [" + String.format("%.2f", rotZ) + " deg. ]\n");
                            // Smiling Probability
                            if (face.getSmilingProbability() > 0) {
                                float SmilingProbability = face.getSmilingProbability();
                                builder.append("SmilingProbability [" + String.format("%.2f", SmilingProbability + "]\n"))
                            }
                            // left eye
                            if (face.getLeftEyeOpenProbability() > 0) {
                                float leftEyeOpenProbability = face.getLeftEyeOpenProbability();
                                builder.append("leftEyeOpenProbability [" + String.format("%.2f", leftEyeOpenProbability + "]\n"))
                            }
                            // right eye
                            if (face.getRightEyeOpenProbability() > 0) {
                                float getRightEyeOpenProbability = face.getRightEyeOpenProbability();
                                builder.append("rightEyeOpenProbability [" + String.format("%.2f", getRightEyeOpenProbability + "]\n"))
                            }
                            builder.append("\n");
                        }
                    }
                })
                .addOnFailureListener(e -> {
                    StringBuilder builder1 = new StringBuilder();
                    builder1.append("Sorry !! There is an error!");
                    ShowDetection("Face",builder,false);
                    Toast.makeText(MainActivity.this, "Face detection failed: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                });

    }

    private void ShowDetection(String face, StringBuilder builder, boolean success) {

    }


}