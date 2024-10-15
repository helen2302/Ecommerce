package com.csci318.ecommerce.shipment.controller;

import com.csci318.ecommerce.shipment.model.Shipment;
import com.csci318.ecommerce.shipment.publisher.ShipmentPublisher;
import com.csci318.ecommerce.shipment.service.ShipmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/shipments")
public class ShipmentController {
    @Autowired
    ShipmentService shipmentService;
    @Autowired
    ShipmentPublisher shipmentPublisher;

    //Get shipment by customerId
    @GetMapping("/customers/{customerId}")
    public List<Shipment> getShipmentByCustomerId(@PathVariable Long customerId) {
        return shipmentService.getShipmentByCustomerId(customerId);
    }

    @GetMapping
    public ResponseEntity<List<Shipment>> getAllShipments() {
        List<Shipment> shipments = shipmentService.getAllShipments();
        System.out.println(shipmentService.getAllShipments());
        return ResponseEntity.ok(shipments);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteShipment(@PathVariable Long id) {
        try {
            shipmentService.deleteShipment(id);
            return ResponseEntity.ok("Shipment deleted successfully");
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build(); // Return 404 if shipment not found
        }
    }

    // Customer confirm shipment
    @PostMapping("/{id}/confirm")
    public ResponseEntity<String> confirmShipment(@PathVariable Long id, @RequestParam boolean confirm) {
        try {
            if (confirm) {
                shipmentPublisher.confirmShipment(id, confirm);
                return ResponseEntity.ok("Shipment confirmation processed successfully");
            } else {
                // Handle cancellation logic if necessary
                shipmentPublisher.confirmShipment(id, confirm); // You can implement this method to handle the cancellation
                return ResponseEntity.ok("Order has been canceled");
            }
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage()); // Return 400 for bad requests
        }
    }

}
