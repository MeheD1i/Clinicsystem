package com.example.clinicsystem;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AddMaterialsActivity extends AppCompatActivity {

    private EditText materialNameEditText;
    private EditText quantityEditText;
    private Button addMaterialButton;

    private FirebaseAuth firebaseAuth;
    private DatabaseReference materialsInventoryRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_materials);

        materialNameEditText = findViewById(R.id.materialNameEditText);
        quantityEditText = findViewById(R.id.quantityEditText);
        addMaterialButton = findViewById(R.id.addMaterialButton);

        firebaseAuth = FirebaseAuth.getInstance();
        String currentUid = firebaseAuth.getCurrentUser().getUid();
        materialsInventoryRef = FirebaseDatabase.getInstance().getReference().child("MaterialsInventory").child(currentUid);

        addMaterialButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addMaterialToInventory();
            }
        });
    }

    private void addMaterialToInventory() {
        String materialName = materialNameEditText.getText().toString().trim();
        String quantityString = quantityEditText.getText().toString().trim();

        if (!materialName.isEmpty() && !quantityString.isEmpty()) {
            int quantity = Integer.parseInt(quantityString);

            String materialId = materialsInventoryRef.push().getKey();
            Material material = new Material(materialId, materialName, quantity);

            materialsInventoryRef.child(materialId).setValue(material);
            Toast.makeText(this, "Material added to inventory", Toast.LENGTH_SHORT).show();

            materialNameEditText.setText("");
            quantityEditText.setText("");
        } else {
            Toast.makeText(this, "Please enter material name and quantity", Toast.LENGTH_SHORT).show();
        }
    }
}
