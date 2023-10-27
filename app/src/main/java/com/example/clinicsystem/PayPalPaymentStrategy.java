package com.example.clinicsystem;

public class PayPalPaymentStrategy implements PaymentStrategy {

    private String email;

    public PayPalPaymentStrategy(String email) {
        this.email = email;
    }

    @Override
    public boolean processPayment(double amount) {
        // Implement PayPal payment processing logic here
        // You can connect to the PayPal API or handle the payment as needed

        // For this example, just print a message to simulate payment processing
        System.out.println("Processing PayPal payment...");
        System.out.println("PayPal Email: " + email);
        System.out.println("Amount: " + amount);

        // Simulate a successful payment
        return true;
    }
}
