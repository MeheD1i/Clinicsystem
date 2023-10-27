package com.example.clinicsystem;

import android.content.Context;
import android.content.Intent;

public class Blood implements DiagnosticReport {
    private Context context;

    public Blood(Context context) {
        this.context = context;
    }

    @Override
    public void displayReport() {
        Intent intent = new Intent(context, blood2.class);
        context.startActivity(intent);
    }
}
