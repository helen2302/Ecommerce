package com.csci318.ecommerce.payment.listener;

import com.csci318.ecommerce.order.model.Order;
import com.csci318.ecommerce.payment.service.PaymentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import java.util.function.Consumer;

@Service
public class PaymentListener {
    @Autowired
    PaymentService PaymentService;
    private static final Logger log = LoggerFactory.getLogger(PaymentListener.class);
    @Bean
    public Consumer<Order> consume() {
        return payload -> {
            log.info(payload.toString());
            PaymentService.createPayment(payload);
        };
    }
}
