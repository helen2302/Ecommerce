package com.csci318.ecommerce.feedback.model;

import lombok.Data;

@Data
public class RatingByProduct {
    Long productId;
    double rating;
}
