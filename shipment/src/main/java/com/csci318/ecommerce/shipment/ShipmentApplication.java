package com.csci318.ecommerce.shipment;

import com.csci318.ecommerce.shipment.model.Shipment;
import com.csci318.ecommerce.shipment.repository.ShipmentRepository;
import com.csci318.ecommerce.shipment.service.ShipmentService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@EnableKafka
public class ShipmentApplication {

	public static void main(String[] args) {
		SpringApplication.run(ShipmentApplication.class, args);
	}
	@Bean
	public RestTemplate restTemplate(RestTemplateBuilder builder) {
		return builder.build();
	}

	@Bean
	public CommandLineRunner loadDatabase(ShipmentService shipmentService, ShipmentRepository shipmentRepository) {
		return args -> {
			// First Shipment: orderId is 1, customerId is 1
			Shipment shipment1 = new Shipment(1L, 1L);
			shipmentRepository.save(shipment1);
			// Initialize and ensure shipment data is fully loaded
			System.out.println("Shipment1: " + shipment1);
		};
	}
}
