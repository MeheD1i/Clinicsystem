// pAppoint.java
package com.example.clinicsystem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Arrays;
import java.util.Calendar;

public class pAppoint extends AppCompatActivity implements
        AdapterView.OnItemSelectedListener {
    TextView e1;
    Spinner s2;
    Button button2;
    private DatePickerDialog datePickerDialog;
    Button button2_1;
    DatabaseReference dentalRef;
    private FirebaseAuth firebaseAuth;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pappoint);
        e1 = (TextView) findViewById(R.id.paetxt1);
        initDatePicker();
        button2_1 = (Button) findViewById(R.id.button1);
        s2 = findViewById(R.id.paetxt2);
        ArrayAdapter<CharSequence> adapter =
                ArrayAdapter.createFromResource(this, R.array.times, android.R.layout.simple_spinner_item);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        s2.setAdapter(adapter);
        s2.setOnItemSelectedListener(this);
        button2 = (Button) findViewById(R.id.bttn2);
        TextView t1 = findViewById(R.id.ptxt1);
        dentalRef = FirebaseDatabase.getInstance().getReference().child("Patients");
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                insertPatientData();
            }
        });
        firebaseAuth = FirebaseAuth.getInstance();
        fetchUserRoleAndName();
    }

    private void fetchUserRoleAndName() {
        String uid = firebaseAuth.getCurrentUser().getUid();
        DatabaseReference userRef = FirebaseDatabase.getInstance().getReference("users").child(uid);

        userRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    String name = snapshot.child("name").getValue(String.class);
                    e1.setText(name);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void initDatePicker() {
        DatePickerDialog.OnDateSetListener dateSetListener = new
                DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month,
                                          int day) {
                        month = month + 1;
                        String date = makeDateString(day, month, year);
                        button2_1.setText(date);
                    }
                };
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);
        int style = AlertDialog.THEME_HOLO_LIGHT;
        datePickerDialog = new DatePickerDialog(this, style, dateSetListener,
                year, month, day);
    }

    private String makeDateString(int day, int month, int year) {
        return getMonthFormat(month) + " " + day + " " + year;
    }

    private String getMonthFormat(int month) {
        if (month == 1)
            return "JAN";
        if (month == 2)
            return "FEB";
        if (month == 3)
            return "MAR";
        if (month == 4)
            return "APR";
        if (month == 5)
            return "MAY";
        if (month == 6)
            return "JUN";
        if (month == 7)
            return "JUL";
        if (month == 8)
            return "AUG";
        if (month == 9)
            return "SEP";
        if (month == 10)
            return "OCT";
        if (month == 11)
            return "NOV";
        if (month == 12)
            return "DEC";
        return "JAN";
    }

    public void openDatePicker(View view) {
        datePickerDialog.show();
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i,
                               long l) {
        String text = adapterView.getItemAtPosition(i).toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
    }

    private void insertPatientData() {
        String name1 = e1.getText().toString();
        String time1 = s2.getSelectedItem().toString();
        String date = button2_1.getText().toString();

        // Get the current user's UID
        FirebaseUser currentUser = firebaseAuth.getCurrentUser();
        if (currentUser != null) {
            String uid = currentUser.getUid();

            // Pass the UID when saving the appointment
            checkAvailability(name1, time1, date, uid);
        } else {
            Toast.makeText(pAppoint.this, "User not logged in.", Toast.LENGTH_SHORT).show();
        }
    }

    private void checkAvailability(String name1, String time1, String date, String uid) {
        TextView t1 = findViewById(R.id.ptxt1);

        dentalRef.orderByChild("time1").equalTo(time1).addListenerForSingleValueEvent
                (new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        boolean dateAlreadyBooked = false;
                        if (dataSnapshot.exists()) {
                            for (DataSnapshot ds : dataSnapshot.getChildren()) {
                                patient2 patient = ds.getValue(patient2.class);
                                if (patient.getDate().equals(date)) {
                                    dateAlreadyBooked = true;
                                    break;
                                }
                            }
                        }
                        if (dateAlreadyBooked) {
                            runOnUiThread(new Runnable() {
                                public void run() {
                                    t1.setText("Date and Time slot is already booked.");
                                }
                            });
                        } else {
                            saveAppointment(name1, time1, date, uid);

                            t1.setText("Patient " + name1 + " your appointment date is " + date + " at " + time1);
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Toast.makeText(pAppoint.this, "Error: " +
                                databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void saveAppointment(String name1, String time1, String date, String uid) {
        DatabaseReference newAppointmentRef = dentalRef.push();
        newAppointmentRef.child("name1").setValue(name1);
        newAppointmentRef.child("time1").setValue(time1);
        newAppointmentRef.child("date").setValue(date);
        newAppointmentRef.child("uid").setValue(uid); // Store the user's UID
    }
}
