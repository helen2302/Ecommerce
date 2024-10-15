package com.csci318.ecommerce.vendor.repository;

import com.csci318.ecommerce.vendor.model.Vendor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VendorRepository extends JpaRepository<Vendor, Long> {

}
