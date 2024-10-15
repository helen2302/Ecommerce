package com.csci318.ecommerce.vendor;
import com.csci318.ecommerce.vendor.model.Product;
import com.csci318.ecommerce.vendor.model.Vendor;
import com.csci318.ecommerce.vendor.repository.ProductRepository;
import com.csci318.ecommerce.vendor.repository.VendorRepository;
import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.boot.CommandLineRunner;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.Collections;
import java.util.Properties;

@SpringBootApplication
public class VendorApplication { 

	public static void main(String[] args) {
		org.springframework.boot.SpringApplication.run(VendorApplication.class, args);
	}

	@Bean
	public RestTemplate restTemplate(RestTemplateBuilder builder) {
		return builder.build();
	}

	@Bean
	public CommandLineRunner loadDatabase(VendorRepository vendorRepository, ProductRepository productRepository) throws Exception {
		return args -> {
			Vendor vendor1 = new Vendor();
			vendor1.setName("Tech Supplies Co.");
			vendor1.setAddress("123 Tech Ave, Silicon Valley, CA");

			Product product1 = new Product("Laptop", 1200.00, 50L, vendor1);
			Product product2 = new Product("Smartphone", 800.00, 30L, vendor1);

			vendor1.setProducts(Arrays.asList(product1, product2));
			vendorRepository.save(vendor1);

			System.out.println(productRepository.findById(product1.getId()).orElseThrow());
			System.out.println(productRepository.findById(product2.getId()).orElseThrow());
			System.out.println(vendorRepository.findById(vendor1.getId()).orElseThrow());

// Second Vendor with products
			Vendor vendor2 = new Vendor();
			vendor2.setName("Office World");
			vendor2.setAddress("456 Office St, New York, NY");

			Product product3 = new Product("Office Chair", 150.00, 20L, vendor2);
			Product product4 = new Product("Desk Lamp", 40.00, 100L, vendor2);

			vendor2.setProducts(Arrays.asList(product3, product4));
			vendorRepository.save(vendor2);

			System.out.println(productRepository.findById(product3.getId()).orElseThrow());
			System.out.println(productRepository.findById(product4.getId()).orElseThrow());
			System.out.println(vendorRepository.findById(vendor2.getId()).orElseThrow());
		};
	}
}