package com.csci318.ecommerce.customer.model;


import jakarta.persistence.Embeddable;

@Embeddable
// CardDetails.java (Value Object)
public class CardDetails {
    private String cardNumber;
    private String cardHolderName;
    private String expiryDate;
    private String cvv;

    public CardDetails() {}
    public CardDetails(String cardNumber, String cardHolderName, String expiryDate, String cvv) {
        this.cardNumber = cardNumber;
        this.cardHolderName = cardHolderName;
        this.expiryDate = expiryDate;
        this.cvv = cvv;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public String getCardHolderName() {
        return cardHolderName;
    }

    public String getExpiryDate() {
        return expiryDate;
    }

    public String getCvv() {
        return cvv;
    }

    @Override
    public String toString() {
        return "CardDetails{" +
                "cardNumber='" + cardNumber + '\'' +
                ", cardHolderName='" + cardHolderName + '\'' +
                ", expiryDate='" + expiryDate + '\'' +
                ", cvv='" + cvv + '\'' +
                '}';
    }

    // Card validation methods (optional, can be expanded as needed)
    public boolean isValidCard() {
        return !cardNumber.isEmpty() && !expiryDate.isEmpty() && !cvv.isEmpty();
    }
}

