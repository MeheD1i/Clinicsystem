package com.example.clinicsystem;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.InputStream;
public class Diagnostic extends AppCompatActivity {
    EditText re1;
    Button bt1, bt2;
    ImageView img1;
    Uri filepath;
    StorageReference storageReference;
    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diagnostic);
        re1 = findViewById(R.id.retxt1);
        img1 = (ImageView)findViewById(R.id.imageView);
        bt1 = (Button)findViewById(R.id.rbttn1);
        bt2 = (Button)findViewById(R.id.rbttn2);
        bt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                seletImage();
            }
        });
        bt2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                uploadImage();
            }
        });
    }
    private void uploadImage() {
        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Uploading file.....");
        progressDialog.show();
        String customImageName = re1.getText().toString().trim();
        storageReference = FirebaseStorage.getInstance().getReference("Report_images"+customImageName+".jpg");
        storageReference.putFile(filepath)
                .addOnSuccessListener(new
                                              OnSuccessListener<UploadTask.TaskSnapshot>() {
                                                  @Override
                                                  public void onSuccess(UploadTask.TaskSnapshot
                                                                                taskSnapshot) {
                                                      img1.setImageURI(filepath);
                                                      Toast.makeText(Diagnostic.this, "Successfully Uploaded", Toast.LENGTH_SHORT).show();
                                                      if (progressDialog.isShowing())
                                                          progressDialog.dismiss();
                                                  }
                                              }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        if (progressDialog.isShowing())
                            progressDialog.dismiss();
                        Toast.makeText(Diagnostic.this, "Failed to upload",
                                Toast.LENGTH_SHORT).show();
                    }
                });
    }
    private void seletImage(){
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent,100);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode,
                                    @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 100 && data != null && data.getData() != null){
            filepath = data.getData();
            img1.setImageURI(filepath);
        }
    }
}