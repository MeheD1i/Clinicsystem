package com.example.clinicsystem;

import android.content.Context;
import android.content.Intent;

public class Tongue implements DiagnosticReport {
    private Context context;

    public Tongue(Context context) {
        this.context = context;
    }

    @Override
    public void displayReport() {
        Intent intent = new Intent(context, tongue2.class);
        context.startActivity(intent);
    }
}
