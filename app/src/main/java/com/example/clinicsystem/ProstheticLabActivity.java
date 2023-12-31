package com.example.clinicsystem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ProstheticLabActivity extends AppCompatActivity {

    private TextView roleTextView;
    private TextView profileNameTextView; // Add this TextView for profile name

    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prosthetic_lab);

        roleTextView = findViewById(R.id.roleTextView);
        profileNameTextView = findViewById(R.id.profileNameTextView); // Initialize the profileNameTextView

        firebaseAuth = FirebaseAuth.getInstance();

        // Fetch the user's role and name from the database
        fetchUserRoleAndName();

        Button inventoryViewButton = findViewById(R.id.inventoryViewButton);
        Button addSuppliesButton = findViewById(R.id.addSuppliesButton);
        Button manageReportButton = findViewById(R.id.buttonManageReport);

        Button signOutButton = findViewById(R.id.signOutButton);
        signOutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Sign out the user
                firebaseAuth.signOut();

                // Redirect to the login activity or any other desired activity
                Intent intent = new Intent(ProstheticLabActivity.this, LoginActivity.class);
                startActivity(intent);
                finish(); // Close the current activity
            }
        });
        inventoryViewButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate to MaterialsInventoryActivity
                Intent intent = new Intent(ProstheticLabActivity.this, MaterialsInventoryActivity.class);
                startActivity(intent);
            }
        });

        addSuppliesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate to AddMaterialsActivity
                Intent intent = new Intent(ProstheticLabActivity.this, AddMaterialsActivity.class);
                startActivity(intent);
            }
        });

        manageReportButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Handle the button click, e.g., navigate to a "Manage Report" activity
                Intent intent = new Intent(ProstheticLabActivity.this, ManageReportActivity.class);
                startActivity(intent);
            }
        });

    }

    private void fetchUserRoleAndName() {
        String uid = firebaseAuth.getCurrentUser().getUid();
        DatabaseReference userRef = FirebaseDatabase.getInstance().getReference("users").child(uid);

        userRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    String role = snapshot.child("role").getValue(String.class);
                    String name = snapshot.child("name").getValue(String.class);

                    // Set the role and profile name in the TextViews
                    roleTextView.setText(role);
                    profileNameTextView.setText(name);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Handle database error
            }
        });

    }

}
