package com.csci318.ecommerce.shipment.service;

import com.csci318.ecommerce.customer.dto.CustomerDTO;
import com.csci318.ecommerce.order.model.Order;
import com.csci318.ecommerce.shipment.model.Shipment;
import com.csci318.ecommerce.shipment.repository.ShipmentRepository;
import java.util.function.Consumer;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class ShipmentService {
    @Autowired
    ShipmentRepository shipmentRepository;
    @Autowired
    RestTemplate restTemplate;

    //Get shipment by customerId
    public List<Shipment> getShipmentByCustomerId(Long customerId){
        String urlCustomer = "http://localhost:8071/customers/";
        // Check if customer exists
        CustomerDTO customer;
        try {
            customer = restTemplate.getForObject(urlCustomer + customerId, CustomerDTO.class);
        } catch (RestClientException e) {
            throw new RuntimeException("Failed to fetch customer with id: " + customerId, e);
        }

        if (customer == null) {
            throw new RuntimeException("Can not create an order because no customer with id: " + customerId);
        }
        return shipmentRepository.findAllByCustomerId(customerId);
    }
    // Method to get all shipments
    public List<Shipment> getAllShipments() {
        return shipmentRepository.findAll();
    }
    //create shipment
    public void createShipment(Order order) {
        // Assuming Shipment has a constructor that takes an Order or relevant fields
        Shipment shipment = new Shipment();
        shipment.setOrderId(order.getId()); // Assuming the Shipment has an orderId field
        shipment.setCustomerId(order.getCustomerId()); // Set status or other necessary fields
        System.out.println("Save: " + shipment);
        // Save the shipment to the repository
        shipmentRepository.save(shipment);
    }
    public void deleteShipment(Long shipmentId) {
        // Check if shipment exists
        if (!shipmentRepository.existsById(shipmentId)) {
            throw new RuntimeException("Shipment not found with ID: " + shipmentId);
        }

        // Delete the shipment
        shipmentRepository.deleteById(shipmentId);
        System.out.println("Shipment with ID: " + shipmentId + " has been deleted");
    }

}
