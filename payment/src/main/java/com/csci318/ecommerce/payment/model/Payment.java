package com.csci318.ecommerce.payment.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;


@Entity
@Data
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long paymentId;
    @Column
    private Long customerId;
    @Column
    private Long orderId;
    @Column
    private double amount;
    @Column
    private String cardNumber;
    @Column
    private LocalDateTime paymentDate;

    public Payment () {
        this.paymentDate = LocalDateTime.now();
    }
    public Payment(Long orderId, Long customerId) {
        this.orderId = orderId;
        this.customerId = customerId;
        this.paymentDate = LocalDateTime.now();
    }


    @Override
    public String toString() {
        return "Payment{" +
                " paymentId=" + paymentId +
                " customerId=" + customerId +
                " orderId=" + orderId +
                " card number=" + cardNumber +
                ", amount=" + amount +
                '}';
    }

}
