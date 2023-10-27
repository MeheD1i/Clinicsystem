package com.example.clinicsystem;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Context;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class Factory extends AppCompatActivity {
    private EditText reportTypeEditText = findViewById(R.id.reportTypeEditText);


    private Button viewReportButton;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        viewReportButton = findViewById(R.id.sbttn6);
        ReportFactory reportFactory = new ReportFactory(this);
        viewReportButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String reportType = reportTypeEditText.getText().toString();
                DiagnosticReport report = reportFactory.createReport(reportType);
                report.displayReport();
            }
        });
    }
}