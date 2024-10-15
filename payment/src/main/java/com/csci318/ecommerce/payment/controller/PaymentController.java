package com.csci318.ecommerce.payment.controller;

import com.csci318.ecommerce.payment.model.Payment;
import com.csci318.ecommerce.payment.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/payments")
public class PaymentController {

    @Autowired
    private PaymentService paymentService;


    // GET method to retrieve payment by customerId
    @GetMapping("/customers/{customerId}")
    public List<Payment> getPaymentByCustomerId(@PathVariable Long customerId) {
        return paymentService.getPaymentByCustomerId(customerId);
    }
}

