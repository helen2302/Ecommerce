package com.csci318.ecommerce.order.publisher;

import com.csci318.ecommerce.order.model.Order;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
public class OrderPublisher {
    private final StreamBridge streamBridge;

    public OrderPublisher(StreamBridge streamBridge) {
        this.streamBridge = streamBridge;
    }

    @TransactionalEventListener
    public void handleOrderPlacingEvent (Order order) {
        streamBridge.send("orderPlacingChannel", order);
        System.out.println("Order placed: " + order);
    }

}
