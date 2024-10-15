package com.csci318.ecommerce.order;

import com.csci318.ecommerce.order.model.Order;
import com.csci318.ecommerce.order.service.OrderService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@EnableKafka
public class OrderApplication {

	public static void main(String[] args) {
		SpringApplication.run(OrderApplication.class, args);
	}
	//
	@Bean
	public RestTemplate restTemplate(RestTemplateBuilder builder) {
		return builder.build();
	}

	@Bean
	public CommandLineRunner loadDatabase(OrderService orderService) {
		return args -> {
			// First Order: customerId is 1, adding productId 1 and 2
			Order order1 = orderService.addProducts(1L, 1L);  // customerId = 1, productId = 1

			// Initialize the lazy collection
			order1.getProductIds().size();

			System.out.println("Order1: " + order1);
		};
	}
}
