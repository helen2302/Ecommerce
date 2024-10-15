package com.csci318.ecommerce.payment.repository;

import com.csci318.ecommerce.payment.model.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {
    List<Payment> findAllPaymentsByCustomerId(@Param("customerId")Long customerId);
}
