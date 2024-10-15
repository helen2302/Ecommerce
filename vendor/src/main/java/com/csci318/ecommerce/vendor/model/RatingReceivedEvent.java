package com.csci318.ecommerce.vendor.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class RatingReceivedEvent {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long ratingId;
    @Column
    private Long vendorId;
    @Column
    private int rating;
}
