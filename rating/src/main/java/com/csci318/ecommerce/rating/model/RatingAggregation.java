package com.csci318.ecommerce.rating.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class RatingAggregation {
    private double totalRating; // Sum of all ratings
    private int count;          // Count of feedbacks received

    public RatingAggregation() {
        this.totalRating = 0.0;
        this.count = 0;
    }
    // Optional: Method to calculate the average rating
    public double getAverageRating() {
        return count == 0 ? 0.0 : totalRating / count; // Prevent division by zero
    }
}

