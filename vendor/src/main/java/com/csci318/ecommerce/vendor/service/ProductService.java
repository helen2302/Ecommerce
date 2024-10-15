package com.csci318.ecommerce.vendor.service;
import com.csci318.ecommerce.vendor.model.FeedbackReceivedEvent;
import com.csci318.ecommerce.vendor.model.Product;
import com.csci318.ecommerce.vendor.model.Vendor;
import com.csci318.ecommerce.vendor.repository.ProductRepository;
import com.csci318.ecommerce.vendor.repository.VendorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductService {

    @Autowired
    private VendorRepository vendorRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private KafkaTemplate<Long, FeedbackReceivedEvent> feedbackKafkaTemplate;

    // Create a product
    public Product createProduct(Product product, Long vendorId) {

        Vendor vendor = vendorRepository.findById(vendorId)
                .orElseThrow(() -> new RuntimeException("Can not create a product because no vendor with id: " + vendorId));
        product.setVendor(vendor);
        vendor.addProducts(product);
        return productRepository.save(product);
    }
    // Get all Products
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    //Get product by Id
    public Product getProductById(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found with id: " + id));
    }



    //Get productDTO by Id
    public Product getProductsById(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Customer not found with id: " + id));

    }

    // View all Products of a Vendor
    public List<Product> getProductsByVendorId(Long vendorId) {
        Vendor vendor = vendorRepository.findById(vendorId)
                .orElseThrow(() -> new RuntimeException("Can not view products of the vendor with id: " + vendorId));

        // Fetch all products based on the list of product IDs
        List<Product> products = productRepository.findProductByVendorId(vendorId);
        return products;
    }
    //Decrease stock
    public boolean decreaseStock(Long productId) {
        Product product = productRepository.findById(productId).orElse(null);
        if (product != null && product.getInStock() > 0) {
            product.setInStock(product.getInStock() - 1);
            productRepository.save(product);
            return true;
        }
        return false; // Product not found or out of stock
    }
    //Increase stock
    // Method to increase the stock for a product by 1
    public void increaseStock(Long productId) {
        Optional<Product> productOptional = productRepository.findById(productId);

        if (productOptional.isEmpty()) {
            throw new IllegalArgumentException("Product with ID: " + productId + " not found.");
        }

        Product product = productOptional.get();
        product.setInStock(product.getInStock() + 1); // Increase stock by 1
        productRepository.save(product);
    }


    public void sendFeedback(Long productId, int rating) {
        FeedbackReceivedEvent feedback = new FeedbackReceivedEvent();
        feedback.setProductId(productId);
        feedback.setRating(rating);

        feedbackKafkaTemplate.send("feedback-topic", productId, feedback); // Use productId as key if needed
    }

}
