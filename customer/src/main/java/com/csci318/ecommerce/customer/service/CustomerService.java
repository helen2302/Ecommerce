package com.csci318.ecommerce.customer.service;


import com.csci318.ecommerce.customer.model.CardDetails;
import com.csci318.ecommerce.customer.model.Customer;
import com.csci318.ecommerce.customer.dto.CustomerDTO;
import com.csci318.ecommerce.customer.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CustomerService {
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private RestTemplate restTemplate;


    public List<CustomerDTO> getAllCustomers() {
        return customerRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public CustomerDTO getCustomerById(Long id) {
        return customerRepository.findById(id).map(this::convertToDTO)
                .orElseThrow(() -> new RuntimeException("Customer not found with id: " + id));

    }

    public CustomerDTO createCustomer(Customer customer) {
        customerRepository.save(customer);
        return convertToDTO(customer);
    }
    public CustomerDTO updateCustomer(CustomerDTO customerDTO) {
        Customer customer = customerRepository.findById(customerDTO.getId())
                .orElseThrow(() -> new RuntimeException("Customer not found with id: " + customerDTO.getId()));
        if(customerDTO.getName() != null) customer.setName(customerDTO.getName());
        if(customerDTO.getEmail() != null) customer.setEmail(customerDTO.getEmail());
        if(customerDTO.getAddress() != null) customer.setAddress(customerDTO.getAddress());
        customerRepository.save(customer);
        return convertToDTO(customer);
    }
//     Convert DTO to Entity
    private Customer convertToEntity(CustomerDTO customerDTO) {
        Customer customer = new Customer();
        customer.setName(customerDTO.getName());
        customer.setEmail(customerDTO.getEmail());
        customer.setAddress(customerDTO.getAddress());
        return customer;
    }

    // Convert Entity to DTO
    private CustomerDTO convertToDTO(Customer customer) {
        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setId(customer.getId());
        customerDTO.setName(customer.getName());
        customerDTO.setAddress(customer.getAddress());
        customerDTO.setEmail(customer.getEmail());
        return customerDTO;
    }
    public void deleteCustomer(Long id) { customerRepository.deleteById(id); }

    public Optional<CardDetails> getCardDetails(Long customerId) {
        Optional<Customer> customerOptional = customerRepository.findById(customerId);
        return customerOptional.map(Customer::getCardDetails);
    }

}
