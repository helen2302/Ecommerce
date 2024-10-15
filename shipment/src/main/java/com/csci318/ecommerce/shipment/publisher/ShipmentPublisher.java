package com.csci318.ecommerce.shipment.publisher;

import com.csci318.ecommerce.order.model.Order;
import com.csci318.ecommerce.shipment.repository.ShipmentRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.stereotype.Service;
import org.springframework.transaction.event.TransactionalEventListener;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Service
public class ShipmentPublisher {
    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private StreamBridge streamBridge;
    @Autowired
    private ShipmentRepository shipmentRepository;


    @TransactionalEventListener
    public void handleOrderShipmentPlacingEvent (Order order) {
        streamBridge.send("orderShipmentChannel", order);
        System.out.println("Order Shipment placed: " + order);
    }
    //confirm shipment
    public void confirmShipment(Long shipmentId, boolean confirm) {
        String orderUrl = "http://localhost:8072/orders/";
        Order order;
        try {
            order = restTemplate.getForObject(orderUrl + shipmentRepository.findOrderIdByShipmentId(shipmentId), Order.class);
        } catch (RestClientException e) {
            throw new RuntimeException("Failed to fetch order with id: " + shipmentRepository.findOrderIdByShipmentId(shipmentId), e);
        }

        if (order == null) {
            throw new RuntimeException("Can not create an order because no order with id: " + shipmentRepository.findOrderIdByShipmentId(shipmentId));
        }
        if (!confirm) {
            order.getProductIds().forEach(productId -> {
                String productApiUrl = "http://localhost:8075/products/" + productId + "/increaseStock"; // Adjust the URL as needed
                try {
                    restTemplate.postForEntity(productApiUrl, null, String.class); // Increase stock by 1
                } catch (RestClientException e) {
                    log.error("e: ", e);
                    throw new RuntimeException("Failed to increase stock for product ID: " + productId, e);
                }
            });
            String deleteOrderApiUrl = "http://localhost:8072/orders/" + order.getId(); // Adjust the URL as needed
            try {
                restTemplate.delete(deleteOrderApiUrl); // Delete the order
            } catch (RestClientException e) {
                throw new RuntimeException("Failed to delete order with ID: " + order.getId(), e);
            }
        }else{
            handleOrderShipmentPlacingEvent(order);
        }
    }
}
