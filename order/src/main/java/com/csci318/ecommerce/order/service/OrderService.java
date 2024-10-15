package com.csci318.ecommerce.order.service;

import com.csci318.ecommerce.customer.dto.CustomerDTO;
import com.csci318.ecommerce.order.publisher.OrderPublisher;
import com.csci318.ecommerce.order.model.Enum.Constants;
import com.csci318.ecommerce.order.model.Order;
//import com.csci318.ecommerce.order.model.relation.ProductDTO;
import com.csci318.ecommerce.order.repository.OrderRepository;
import com.csci318.ecommerce.vendor.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;


import java.util.List;

import static com.csci318.ecommerce.order.model.Enum.Constants.OrderStatus.PENDING;

@Service
public class OrderService {
    @Autowired
    OrderRepository orderRepository;
    @Autowired
    RestTemplate restTemplate;
    @Autowired
    OrderPublisher orderPublisher;

    //Add product to the order to create order
    public Order addProducts(Long customerId, long productId) {
        String urlCustomer = "http://localhost:8071/customers/";
        String urlProduct = "http://localhost:8075/products/";
        String urlDecreaseStock = "http://localhost:8075/products/" + productId + "/decreaseStock"; // URL to decrease stock

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

        // Check if product exists
        Product product;
        try {
            product = restTemplate.getForObject(urlProduct + productId, Product.class);
        } catch (RestClientException e) {
            throw new RuntimeException("Failed to fetch product with id: " + productId, e);
        }

        if (product == null) {
            throw new RuntimeException("Can not create an order because no product with id: " + productId);
        }

        Order order = orderRepository.findFirstOrderByCustomerIdAndStatus(customerId, PENDING).orElse(null);
        if( order == null) {
            order = new Order(customer.getId());
        }
        if(product.getInStock() - 1 < 0) {
            order.setMessage("Product is out of stock!");
        }else{
            // Decrease stock in the ProductService
            ResponseEntity<Void> response = restTemplate.exchange(
                    urlDecreaseStock,
                    HttpMethod.PUT,
                    null,
                    Void.class
            );
            if (response.getStatusCode() != HttpStatus.OK) {
                throw new RuntimeException("Failed to decrease stock for product with id: " + productId);
            }
            product = restTemplate.getForObject(urlProduct + productId, Product.class);
            order.setMessage("Successfully add product!");
            order.addProducts(productId);
            order.setTotalAmount(order.getTotalAmount() + product.getPrice());
        }

        order.setOrderStatus(PENDING);
        orderRepository.save(order);

        return order;
    }

    // Customer can view Order by Id
    public List<Order> viewAllOrdersByCustomerId(Long customerId){
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
        List<Order> ordersByCustomer = orderRepository.findAllOrdersByCustomerId(customerId);
        return ordersByCustomer;
    }


    public ResponseEntity<String> orderCheckOut(@PathVariable Long customerId) {
        try {
            String successMessage = "Order checkout process initiated successfully for customerId: " + customerId;
            // Log message to console
            System.out.println(successMessage);
            //Forward order to shipment
            Order order = orderRepository.findFirstOrderByCustomerIdAndStatus(customerId, PENDING).orElse(null);
            order.setOrderStatus(Constants.OrderStatus.COMPLETED);
            orderRepository.save(order);

            if (order != null && order.getOrderStatus() == Constants.OrderStatus.COMPLETED) {
                orderPublisher.handleOrderPlacingEvent(order);
            } else {
                throw new RuntimeException("No completed order found for customer: " + customerId);
            }
            // Return HTTP response with message
            return ResponseEntity.ok(successMessage);
        } catch (Exception e) {
            // Log error to console
            System.err.println("Error during order checkout: " + e.getMessage());
            // Return HTTP response with error message
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to initiate checkout process");
        }
    }

    // Find order by Id
    public Order getOrderById(Long orderId) {
        return orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found with id: " + orderId));
    }

    //delete order by Id
    public void deleteOrderById(Long orderId) {
        if (!orderRepository.existsById(orderId)) {
            throw new RuntimeException("Order not found with id: " + orderId);
        }
        orderRepository.deleteById(orderId);
    }

}
