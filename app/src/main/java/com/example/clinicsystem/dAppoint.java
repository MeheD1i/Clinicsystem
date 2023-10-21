package com.example.clinicsystem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;
import java.util.List;
public class dAppoint extends AppCompatActivity {
    ListView myListview;
    List<patient2> patient2List;
    DatabaseReference patientRef;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dappoint);
        myListview = findViewById(R.id.myListView);
        patient2List = new ArrayList<>();
        patientRef = FirebaseDatabase.getInstance().getReference("Patients");
        patientRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                patient2List.clear();
                for(DataSnapshot patientdataSnapshot :
                        snapshot.getChildren()){
                    patient2 patient2 =
                            patientdataSnapshot.getValue(patient2.class);
                    patient2List.add(patient2);
                }
                ListAdapter adapter = new
                        ListAdapter(dAppoint.this,patient2List);
                myListview.setAdapter(adapter);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }
}