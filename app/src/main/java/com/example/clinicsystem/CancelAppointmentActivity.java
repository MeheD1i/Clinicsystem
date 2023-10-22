// CancelAppointmentActivity.java
package com.example.clinicsystem;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class CancelAppointmentActivity extends AppCompatActivity {
    private ListView appointmentListView;
    private ArrayAdapter<String> appointmentAdapter;
    private ArrayList<String> appointmentKeys;
    private DatabaseReference dentalRef;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cancel_appointment);

        appointmentListView = findViewById(R.id.appointmentListView);
        appointmentAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1);
        appointmentKeys = new ArrayList<>();

        dentalRef = FirebaseDatabase.getInstance().getReference().child("Patients");
        firebaseAuth = FirebaseAuth.getInstance();

        appointmentListView.setAdapter(appointmentAdapter);

        loadAppointments();

        appointmentListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Retrieve the selected appointment key and attempt to cancel it
                String selectedAppointmentKey = appointmentKeys.get(position);
                cancelAppointment(selectedAppointmentKey);
            }
        });
    }

    // Load the appointments for the current user based on their UID
    private void loadAppointments() {
        FirebaseUser currentUser = firebaseAuth.getCurrentUser();

        if (currentUser != null) {
            String currentUid = currentUser.getUid();
            dentalRef.orderByChild("uid").equalTo(currentUid)
                    .addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            appointmentAdapter.clear();
                            appointmentKeys.clear(); // Clear the list of keys

                            if (dataSnapshot.exists()) {
                                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                                    patient2 appointment = ds.getValue(patient2.class);
                                    String appointmentInfo = "Date: " + appointment.getDate() + ", Time: " + appointment.getTime1();
                                    appointmentAdapter.add(appointmentInfo);

                                    // Store the key of the appointment
                                    appointmentKeys.add(ds.getKey());
                                }
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                            // Handle errors
                            Toast.makeText(CancelAppointmentActivity.this, "Error: " + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
        }
    }

    // Cancel the selected appointment based on the provided appointment key
    private void cancelAppointment(String appointmentKey) {
        dentalRef.child(appointmentKey).removeValue().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                Toast.makeText(CancelAppointmentActivity.this, "Appointment canceled", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(CancelAppointmentActivity.this, "Failed to cancel appointment", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
