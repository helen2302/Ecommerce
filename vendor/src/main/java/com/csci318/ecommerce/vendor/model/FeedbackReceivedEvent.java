package com.csci318.ecommerce.vendor.model;


import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class FeedbackReceivedEvent {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long feedbackId;
    @Column
    private Long productId;
    @Column
    private int rating;

    // Getters and Setters
}

