package com.example.clinicsystem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
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

    private ImageView profilePictureImageView;
    private TextView roleTextView;
    private TextView profileNameTextView; // Add this TextView for profile name
    private TextView emailStatusTextView; // Add this TextView for email status

    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prosthetic_lab);

        profilePictureImageView = findViewById(R.id.profilePictureImageView);
        roleTextView = findViewById(R.id.roleTextView);
        profileNameTextView = findViewById(R.id.profileNameTextView); // Initialize the profileNameTextView
        emailStatusTextView = findViewById(R.id.emailStatusTextView); // Initialize the emailStatusTextView

        firebaseAuth = FirebaseAuth.getInstance();

        // Fetch the user's role and name from the database
        fetchUserRoleAndName();
        // Check and display email verification status
        checkEmailVerificationStatus();
        // Add click action to the "Email is not verified" TextView
        emailStatusTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendEmailVerification();
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
    private void checkEmailVerificationStatus() {
        FirebaseUser user = firebaseAuth.getCurrentUser();
        if (user != null) {
            boolean isEmailVerified = user.isEmailVerified();

            if (isEmailVerified) {
                emailStatusTextView.setText("Email is verified");
            } else {
                emailStatusTextView.setText("Email is not verified (click here to verify)");
            }
        }
    }

    private void sendEmailVerification() {
        FirebaseUser user = firebaseAuth.getCurrentUser();
        if (user != null) {
            user.sendEmailVerification()
                    .addOnCompleteListener(this, task -> {
                        if (task.isSuccessful()) {
                            Toast.makeText(this, "Verification Email Sent", Toast.LENGTH_SHORT).show();
                        }
                    });
        }
    }
}
