package com.csci318.ecommerce.shipment.listener;

import com.csci318.ecommerce.order.model.Order;
import com.csci318.ecommerce.shipment.service.ShipmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.function.Consumer;
@Service
public class ShipmentListener {
    @Autowired
    ShipmentService shipmentService;

    private static final Logger log = LoggerFactory.getLogger(ShipmentListener.class);
    @Bean
    public Consumer<Order> consume() {
        return payload -> {
            log.info(payload.toString());
            shipmentService.createShipment(payload);
        };
    }

}
