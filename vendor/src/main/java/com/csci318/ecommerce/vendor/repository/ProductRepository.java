package com.csci318.ecommerce.vendor.repository;

import com.csci318.ecommerce.vendor.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {
    @Query( "SELECT p FROM Product p WHERE p.vendor.id = :vendorId")
    List<Product> findProductByVendorId(@Param("vendorId") Long vendorId);

}
