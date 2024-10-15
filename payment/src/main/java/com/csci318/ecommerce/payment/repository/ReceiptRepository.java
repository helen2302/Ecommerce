package com.csci318.ecommerce.payment.repository;

import com.csci318.ecommerce.payment.model.Receipt;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ReceiptRepository extends JpaRepository<Receipt, Long> {
    List<Receipt> findAllReceiptsByCustomerId(@Param("customerId")Long customerId);
}
