package com.example.clinicsystem;

public class CreditCardPaymentStrategy implements com.example.clinicsystem.PaymentStrategy {

    private String cardNumber;
    private String cardHolderName;
    private String expirationDate;
    private String cvv;

    public CreditCardPaymentStrategy(String cardNumber, String cardHolderName, String expirationDate, String cvv) {
        this.cardNumber = cardNumber;
        this.cardHolderName = cardHolderName;
        this.expirationDate = expirationDate;
        this.cvv = cvv;
    }

    @Override
    public boolean processPayment(double amount) {
        // Implement credit card payment processing logic here
        // You can connect to a payment gateway or handle the payment as needed

        // For this example, just print a message to simulate payment processing
        System.out.println("Processing credit card payment...");
        System.out.println("Card Number: " + cardNumber);
        System.out.println("Cardholder Name: " + cardHolderName);
        System.out.println("Expiration Date: " + expirationDate);
        System.out.println("CVV: " + cvv);
        System.out.println("Amount: " + amount);

        // Simulate a successful payment
        return true;
    }
}

