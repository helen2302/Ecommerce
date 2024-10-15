package com.csci318.ecommerce.rating.controller;

import com.csci318.ecommerce.rating.model.RatingByVendor;
import com.csci318.ecommerce.rating.service.RatingQueryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class RatingController {
    @Autowired
    private  RatingQueryService ratingQueryService;


    @GetMapping("/rating")
    public ResponseEntity<List<RatingByVendor>> getAllVendorRating() {
        List<RatingByVendor> allRatingByVendor = ratingQueryService.getAllRatingByVendor();
        if (allRatingByVendor != null) {
            return ResponseEntity.ok(allRatingByVendor);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    @GetMapping("/rating/{vendorId}")
    public ResponseEntity<RatingByVendor> getRatingByVendor(@PathVariable Long vendorId) {
        RatingByVendor ratingByVendor = ratingQueryService.getRatingByVendor(vendorId);

        if (ratingByVendor != null) {
            return ResponseEntity.ok(ratingByVendor);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
