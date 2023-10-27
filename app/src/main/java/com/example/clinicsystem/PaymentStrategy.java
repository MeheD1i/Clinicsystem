package com.example.clinicsystem;

public interface PaymentStrategy {
    boolean processPayment(double amount);
}
