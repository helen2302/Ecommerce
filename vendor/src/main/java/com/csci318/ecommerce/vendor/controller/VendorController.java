package com.csci318.ecommerce.vendor.controller;


import com.csci318.ecommerce.vendor.model.Vendor;
import com.csci318.ecommerce.vendor.service.VendorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/vendors")
public class VendorController {
    @Autowired
    private VendorService vendorService;


    @PostMapping
    public Vendor createVendor(@RequestBody Vendor vendor) {
        return vendorService.createVendor(vendor);
    }

    @GetMapping("/{id}")
    public Vendor getVendorById(@PathVariable Long id) {
        return vendorService.getVendorById(id);
    }

    @GetMapping
    public List<Vendor> getAllVendors() {
        return vendorService.getAllVendors();
    }

    @PostMapping("/rating")
    public ResponseEntity<String> sendFeedback(@RequestParam Long vendorId, @RequestParam int rating) {
        // Send the feedback using the service
        vendorService.sendRating(vendorId, rating);
        return ResponseEntity.ok("Rating of vendor is sent successfully");
    }
}

