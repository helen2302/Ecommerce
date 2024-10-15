package com.csci318.ecommerce.payment.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
public class Receipt {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long receiptId;

    @Column
    private Long orderId;

    @Column
    private double amountPaid;

    @Column
    private LocalDateTime paymentDate;

    @Column
    private Long customerId;
    @Column
    private Long paymentId;

    // Default constructor required by JPA
    public Receipt() {}

    // Constructor with parameters
    public Receipt(Long paymentId, Long orderId, double amountPaid, Long customerId, LocalDateTime paymentDate) {
        this.paymentId = paymentId;
        this.orderId = orderId;
        this.amountPaid = amountPaid;
        this.customerId = customerId;
        this.paymentDate = paymentDate;
    }

    @Override
    public String toString() {
        return "Receipt{" +
                "receiptId=" + receiptId +
                ", orderId=" + orderId +
                ", amountPaid=" + amountPaid +
                ", paymentDate=" + paymentDate +
                '}';
    }
}
