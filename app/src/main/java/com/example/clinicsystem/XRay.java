package com.example.clinicsystem;

import android.content.Context;
import android.content.Intent;

public class XRay implements DiagnosticReport {
    private Context context;

    public XRay(Context context) {
        this.context = context;
    }

    @Override
    public void displayReport() {
        Intent intent = new Intent(context, Xr_ay.class);
        context.startActivity(intent);
    }
}
