package com.csci318.ecommerce.vendor.controller;

import com.csci318.ecommerce.vendor.model.Product;
import com.csci318.ecommerce.vendor.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    // Create a product
    @PostMapping
    public Product createProduct(@RequestBody Product product, @RequestParam Long vendorId) {
        return productService.createProduct(product,vendorId);
    }

    // Get all products
    @GetMapping
    public List<Product> getAllProducts() {
        return productService.getAllProducts();
    }

    @GetMapping("/{id}")
    public Product getProductById(@PathVariable Long id) {
        return productService.getProductById(id);
    }


    @GetMapping("/vendor/{vendorId}")
    public List<Product> getProductsByVendorId(@PathVariable Long vendorId) {
        return productService.getProductsByVendorId(vendorId);
    }
    @PutMapping("/{productId}/decreaseStock")
    public ResponseEntity<Void> decreaseStock(@PathVariable Long productId) {
        boolean success = productService.decreaseStock(productId);
        return success ? ResponseEntity.ok().build() : ResponseEntity.notFound().build();
    }

    @PostMapping("/{productId}/increaseStock")
    public ResponseEntity<String> increaseStock(@PathVariable Long productId) {
        try {
            productService.increaseStock(productId);
            return ResponseEntity.ok("Stock increased for product with ID: " + productId);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
    @PostMapping("/feedback")
    public ResponseEntity<String> sendFeedback(@RequestParam Long productId,
                                               @RequestParam int rating) {
        // Send the feedback using the service
        productService.sendFeedback(productId, rating);
        return ResponseEntity.ok("Feedback of product is sent successfully");
    }
}
