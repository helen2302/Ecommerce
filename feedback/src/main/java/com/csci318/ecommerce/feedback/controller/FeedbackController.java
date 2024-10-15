package com.csci318.ecommerce.feedback.controller;

import com.csci318.ecommerce.feedback.model.RatingByProduct;
import com.csci318.ecommerce.feedback.service.FeedbackQueryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class FeedbackController {
    @Autowired
    FeedbackQueryService feedbackQueryService;
    @GetMapping("/feedback")
    public ResponseEntity<List<RatingByProduct>> getProductFeedback() {
        List<RatingByProduct> allRatingByProduct = feedbackQueryService.getAllRatingByProduct();
        if (allRatingByProduct != null) {
            return ResponseEntity.ok(allRatingByProduct);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

}
