package com.csci318.ecommerce.customer.controller;

import com.csci318.ecommerce.customer.dto.CustomerDTO;
import com.csci318.ecommerce.customer.model.CardDetails;
import com.csci318.ecommerce.customer.service.CustomerService;
import com.csci318.ecommerce.customer.model.Customer;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/customers")



public class CustomerController {
    @Autowired
    private CustomerService customerService;

    private static final Logger log
            = LoggerFactory.getLogger(CustomerController.class);

    @GetMapping
    public List<CustomerDTO> getAllCustomers() {
        return customerService.getAllCustomers();
    }

    @GetMapping("/{id}")
    public CustomerDTO getCustomerById(@PathVariable Long id) {
        return customerService.getCustomerById(id);
    }

    @PostMapping
    public CustomerDTO createCustomer(@RequestBody Customer customer) {
        return customerService.createCustomer(customer);
    }

    @PutMapping("/{id}")
    public CustomerDTO updateCustomer(@PathVariable Long id, @RequestBody CustomerDTO customerDTO) {
        return customerService.updateCustomer(customerDTO);
    }

    @DeleteMapping("/{id}")
    public void deleteCustomer(@PathVariable Long id) {
        customerService.deleteCustomer(id);
    }

    // API to get card details by customer ID
    @GetMapping("/{id}/cardDetails")
    public Optional<CardDetails> getCardDetails(@PathVariable Long id) {
        return customerService.getCardDetails(id);
    }
}

