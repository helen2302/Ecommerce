package com.csci318.ecommerce.payment.controller;

import com.csci318.ecommerce.payment.model.Payment;
import com.csci318.ecommerce.payment.model.Receipt;
import com.csci318.ecommerce.payment.service.ReceiptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/receipts")
public class ReceiptController {
    @Autowired
    private ReceiptService receiptService;


    // GET method to retrieve payment by customerId
    @GetMapping("/customers/{customerId}")
    public List<Receipt> getPaymentByCustomerId(@PathVariable Long customerId) {
        return receiptService.getReceiptByCustomerId(customerId);
    }
}
