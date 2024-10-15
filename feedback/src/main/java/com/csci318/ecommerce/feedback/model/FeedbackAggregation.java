package com.csci318.ecommerce.feedback.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class FeedbackAggregation {
    private double totalRating; // Sum of all ratings
    private int count;          // Count of feedbacks received

    public FeedbackAggregation() {
        this.totalRating = 0.0;
        this.count = 0;
    }
    // Optional: Method to calculate the average rating
    public double getAverageRating() {
        return count == 0 ? 0.0 : totalRating / count; // Prevent division by zero
    }
}

