package com.csci318.ecommerce.payment.service;

import com.csci318.ecommerce.customer.dto.CustomerDTO;
import com.csci318.ecommerce.payment.model.Payment;
import com.csci318.ecommerce.payment.model.Receipt;
import com.csci318.ecommerce.payment.repository.ReceiptRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class ReceiptService {
    @Autowired
    private ReceiptRepository receiptRepository;
    @Autowired
    RestTemplate restTemplate;
    public List<Receipt> getReceiptByCustomerId(Long customerId) {
        String urlCustomer = "http://localhost:8071/customers/";
        // Check if customer exists
        CustomerDTO customerDTO;
        try {
            customerDTO = restTemplate.getForObject(urlCustomer + customerId, CustomerDTO.class);
        } catch (RestClientException e) {
            throw new RuntimeException("Failed to fetch customer with id: " + customerId, e);
        }

        if (customerDTO == null) {
            throw new RuntimeException("Can not create an order because no customer with id: " + customerId);
        }
        return receiptRepository.findAllReceiptsByCustomerId(customerId);
    }
}
