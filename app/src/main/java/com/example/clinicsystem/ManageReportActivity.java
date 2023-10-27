package com.example.clinicsystem;


import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ManageReportActivity extends AppCompatActivity {

    private EditText reportEditText;
    private Button sendReportButton;

    // Firebase
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_report);

        reportEditText = findViewById(R.id.editTextReport);
        sendReportButton = findViewById(R.id.buttonSendReport);

        // Initialize Firebase Realtime Database
        mDatabase = FirebaseDatabase.getInstance().getReference();

        sendReportButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Get the report text from the EditText
                String reportText = reportEditText.getText().toString();

                // Create a reference to the "reports" node in the database
                DatabaseReference reportsRef = mDatabase.child("reports");

                // Push a new report to the database
                DatabaseReference newReportRef = reportsRef.push();
                newReportRef.setValue(reportText);

                // Finish the activity
                finish();
            }
        });
    }
}
