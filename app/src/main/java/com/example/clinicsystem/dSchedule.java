package com.example.clinicsystem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import java.io.File;
import java.io.IOException;
public class dSchedule extends AppCompatActivity {
    EditText se1;
    Button sb1;
    ImageView img2;
    StorageReference storageReference1;
    ProgressDialog progressDialog1;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dschedule);
        se1 = findViewById(R.id.lsetxt1);
        sb1 = findViewById(R.id.sbttn1);
        img2 = findViewById(R.id.imageView2);
        sb1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressDialog1 = new ProgressDialog(dSchedule.this);
                progressDialog1.setMessage("Fetching image....");
                progressDialog1.setCancelable(false);
                progressDialog1.show();
                String imageID = se1.getText().toString();
                storageReference1 =
                        FirebaseStorage.getInstance().getReference("Report_images"+imageID+".jpg");
                try {
                    File localfile = File.createTempFile("temfile",".jpg");
                    storageReference1.getFile(localfile)
                            .addOnSuccessListener(new
                                                          OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                                                              @Override
                                                              public void
                                                              onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                                                                  if(progressDialog1.isShowing())
                                                                      progressDialog1.dismiss();
                                                                  Bitmap bitmap =
                                                                          BitmapFactory.decodeFile(localfile.getAbsolutePath());
                                                                  img2.setImageBitmap(bitmap);
                                                              }
                                                          }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    if(progressDialog1.isShowing())
                                        progressDialog1.dismiss();
                                    Toast.makeText(dSchedule.this, "Failed to retrieve", Toast.LENGTH_SHORT).show();
                                }
                            });
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }
}