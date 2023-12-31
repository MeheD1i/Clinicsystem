package com.example.clinicsystem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Button;
import android.widget.Toast;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class RadiologyLabActivity extends AppCompatActivity {

    private TextView roleTextView;
    private TextView profileNameTextView; // Add this TextView for profile name

    private FirebaseAuth firebaseAuth;
    Button b3,b4,b5;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_radiology_lab);

        roleTextView = findViewById(R.id.roleTextView);
        profileNameTextView = findViewById(R.id.profileNameTextView); // Initialize the profileNameTextView


        firebaseAuth = FirebaseAuth.getInstance();
        b3 = findViewById(R.id.abttn3);
        b4 =  findViewById(R.id.sbttn4);
        b5 =  findViewById(R.id.sbttn5);
        Button signOutButton = findViewById(R.id.signOutButton);

        // Fetch the user's role and name from the database
        fetchUserRoleAndName();
        b3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),Diagnostic.class));
            }
        });

        b5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),Factory.class));
            }
        });

        signOutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Sign out the user
                firebaseAuth.signOut();

                // Redirect to the login activity or any other desired activity
                Intent intent = new Intent(RadiologyLabActivity.this, LoginActivity.class);
                startActivity(intent);
                finish(); // Close the current activity
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
