package com.csci318.ecommerce.payment;

import com.csci318.ecommerce.payment.model.Payment;
import com.csci318.ecommerce.payment.repository.PaymentRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;

@SpringBootApplication
public class PaymentApplication {

	public static void main(String[] args) {
		org.springframework.boot.SpringApplication.run(PaymentApplication.class, args);
	}

	@Bean
	public RestTemplate restTemplate(RestTemplateBuilder builder) {
		return builder.build();
	}

	@Bean
	public CommandLineRunner loadDatabase(PaymentRepository paymentRepository) throws Exception {
		return args -> {
// First Payment
			Payment payment1 = new Payment(2L,2L);
			paymentRepository.save(payment1);
			System.out.println(paymentRepository.findById(payment1.getPaymentId()).orElseThrow());

// Second Payment
			Payment payment2 = new Payment(2L,2L);
			paymentRepository.save(payment2);
			System.out.println(paymentRepository.findById(payment2.getPaymentId()).orElseThrow());
		};
	}
}