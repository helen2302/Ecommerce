package com.csci318.ecommerce.payment.service;

import com.csci318.ecommerce.customer.dto.CustomerDTO;
import com.csci318.ecommerce.customer.model.CardDetails;
import com.csci318.ecommerce.customer.model.Customer;
import com.csci318.ecommerce.order.model.Order;
import com.csci318.ecommerce.payment.model.Payment;
import com.csci318.ecommerce.payment.model.Receipt;
import com.csci318.ecommerce.payment.repository.PaymentRepository;

import com.csci318.ecommerce.payment.repository.ReceiptRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;


import java.time.LocalDateTime;
import java.util.List;

@Service
public class PaymentService {
    @Autowired
    private PaymentRepository paymentRepository;
    @Autowired
    private ReceiptRepository receiptRepository;
    @Autowired
    RestTemplate restTemplate;


    //@Transactional
    public Payment createPayment(Order order) {
        Payment payment = new Payment();
        payment.setOrderId(order.getId());
        payment.setCustomerId(order.getCustomerId());
        payment.setAmount(order.getTotalAmount());

        String urlCustomer = "http://localhost:8071/customers/";
        CardDetails cardDetails = restTemplate.getForObject(urlCustomer + order.getCustomerId()+ "/cardDetails", CardDetails.class);
        payment.setCardNumber(cardDetails.getCardNumber());
        paymentRepository.save(payment);
        Receipt receipt = new Receipt( payment.getPaymentId(),payment.getOrderId(), payment.getAmount(), payment.getCustomerId(), payment.getPaymentDate());
        receiptRepository.save(receipt);
        return payment;
    }

    public List<Payment> getPaymentByCustomerId(Long customerId) {
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
        return paymentRepository.findAllPaymentsByCustomerId(customerId);
    }
}


