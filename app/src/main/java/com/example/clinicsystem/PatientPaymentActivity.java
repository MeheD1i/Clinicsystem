package com.example.clinicsystem;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class PatientPaymentActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_payment);

        Button creditCardButton = findViewById(R.id.creditCardButton);
        Button payPalButton = findViewById(R.id.payPalButton);

        creditCardButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // User selected Credit Card
                // You can start a new activity for Credit Card payment here.
                Intent intent = new Intent(PatientPaymentActivity.this, CreditCardPaymentActivity.class);
                startActivity(intent);
            }
        });

        payPalButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // User selected PayPal
                // You can start a new activity for PayPal payment here.
                Intent intent = new Intent(PatientPaymentActivity.this, PayPalPaymentActivity.class);
                startActivity(intent);
            }
        });
    }
}