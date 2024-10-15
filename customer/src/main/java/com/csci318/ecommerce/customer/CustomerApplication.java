package com.csci318.ecommerce.customer;

import com.csci318.ecommerce.customer.model.CardDetails;
import com.csci318.ecommerce.customer.model.Customer;
import com.csci318.ecommerce.customer.repository.CustomerRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class CustomerApplication {

	public static void main(String[] args) {
		org.springframework.boot.SpringApplication.run(CustomerApplication.class, args);
	}

	@Bean
	public RestTemplate restTemplate(RestTemplateBuilder builder) {
		return builder.build();
	}

	@Bean
	public CommandLineRunner loadDatabase(CustomerRepository customerRepository) throws Exception {
		return args -> {
			// Create CardDetails for customer1
			CardDetails cardDetails1 = new CardDetails("4111111111111111", "John Doe", "12/25", "123");
			Customer customer1 = new Customer();
			customer1.setName("John Doe");
			customer1.setEmail("john.doe@example.com");
			customer1.setAddress("123 Main St");
			customer1.setPassword("password123");
			customer1.setCardDetails(cardDetails1); // Set card details for customer1
			customerRepository.save(customer1);
			System.out.println(customerRepository.findById(customer1.getId()).orElseThrow());

			// Create CardDetails for customer2
			CardDetails cardDetails2 = new CardDetails("5555555555554444", "Jane Smith", "11/26", "456");
			Customer customer2 = new Customer();
			customer2.setName("Jane Smith");
			customer2.setEmail("jane.smith@example.com");
			customer2.setAddress("456 Oak St");
			customer2.setPassword("password456");
			customer2.setCardDetails(cardDetails2); // Set card details for customer2
			customerRepository.save(customer2);
			System.out.println(customerRepository.findById(customer2.getId()).orElseThrow());
		};
	}
}
