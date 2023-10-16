package com.example.clinicsystem;

public class User {
    private String role;
    private String name; // Add the name field

    public User() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public User(String role, String name) {
        this.role = role;
        this.name = name;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}


