package com.example.clinicsystem;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.HashMap;
import java.util.Map;

public class SingletonMaterialsInventory {
    private static SingletonMaterialsInventory instance;
    private Map<String, Integer> materialsInventory;
    private DatabaseReference materialsInventoryRef;

    private SingletonMaterialsInventory() {
        materialsInventory = new HashMap<>();
        materialsInventoryRef = FirebaseDatabase.getInstance().getReference().child("materialsInventory");
        fetchMaterialsInventory();
    }

    public static SingletonMaterialsInventory getInstance() {
        if (instance == null) {
            instance = new SingletonMaterialsInventory();
        }
        return instance;
    }

    public void addMaterial(String material, int quantity) {
        if (materialsInventory.containsKey(material)) {
            quantity += materialsInventory.get(material);
        }
        materialsInventory.put(material, quantity);
        materialsInventoryRef.child(material).setValue(quantity);
    }

    public Map<String, Integer> getMaterialsInventory() {
        return materialsInventory;
    }

    private void fetchMaterialsInventory() {
        materialsInventoryRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                materialsInventory.clear();
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    String material = ds.getKey();
                    int quantity = ds.getValue(Integer.class);
                    materialsInventory.put(material, quantity);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Handle errors
            }
        });
    }
}

