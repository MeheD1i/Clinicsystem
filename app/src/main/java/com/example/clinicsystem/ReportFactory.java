package com.example.clinicsystem;

import android.content.Context;

public class ReportFactory {
    private Context context;

    public ReportFactory(Context context) {
        this.context = context;
    }

    public DiagnosticReport createReport(String reportType) {
        if (reportType.equalsIgnoreCase("X-ray")) {
            return new XRay(context);
        } else if (reportType.equalsIgnoreCase("Blood")) {
            return new Blood(context);
        } else if (reportType.equalsIgnoreCase("Tongue")) {
            return new Tongue(context);
        } else {
            throw new IllegalArgumentException("Invalid report type");
        }
    }
}
