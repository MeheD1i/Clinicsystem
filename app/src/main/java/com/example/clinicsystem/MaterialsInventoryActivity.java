package com.example.clinicsystem;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MaterialsInventoryActivity extends AppCompatActivity {

    private ListView materialsListView;
    private FirebaseAuth firebaseAuth;
    private DatabaseReference materialsInventoryRef;
    private ArrayList<String> materialsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_materials_inventory);

        materialsListView = findViewById(R.id.materialsListView);
        materialsList = new ArrayList<>();

        firebaseAuth = FirebaseAuth.getInstance();
        String currentUid = firebaseAuth.getCurrentUser().getUid();
        materialsInventoryRef = FirebaseDatabase.getInstance().getReference().child("MaterialsInventory").child(currentUid);

        // Initialize an ArrayAdapter to display the materials
        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, materialsList);
        materialsListView.setAdapter(arrayAdapter);

        materialsInventoryRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                materialsList.clear(); // Clear the list before adding items

                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    Material material = ds.getValue(Material.class);
                    if (material != null) {
                        String materialInfo = "Material: " + material.getMaterialName() + ", Quantity: " + material.getQuantity();
                        materialsList.add(materialInfo);
                    }
                }
                arrayAdapter.notifyDataSetChanged(); // Notify the adapter of changes
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle errors
                Toast.makeText(MaterialsInventoryActivity.this, "Error: " + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
