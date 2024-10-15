package com.csci318.ecommerce.order.controller;

import com.csci318.ecommerce.order.model.Order;
import com.csci318.ecommerce.order.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrderController {
    @Autowired
    private OrderService orderService;

    @PostMapping("/add/products")
    public Order addProducts(@RequestParam Long customerId, @RequestParam Long productId) {
        return orderService.addProducts(customerId, productId);
    }
    //get Order by Id
    @GetMapping("/customers/{customerId}")
    public List<Order> viewAllOrdersByCustomerId(@PathVariable Long customerId) {
        return orderService.viewAllOrdersByCustomerId(customerId);
    }
    //Check Out API -> generate a shipment
//    @PostMapping("/checkout/customers/{customerId}")
//    public void orderCheckout(@PathVariable Long customerId){
//        orderService.orderCheckOut(customerId);
//    }
    @PostMapping("/checkout/customers/{customerId}")
    public ResponseEntity<String> orderCheckout(@PathVariable Long customerId) {
        orderService.orderCheckOut(customerId);
        return ResponseEntity.ok("Checkout successfully");
    }


    @GetMapping("/{orderId}")
    public Order getOrderById(@PathVariable Long orderId) {
        return orderService.getOrderById(orderId);
    }

    @DeleteMapping("/{orderId}")
    public ResponseEntity<Void> deleteOrderById(@PathVariable Long orderId) {
        orderService.deleteOrderById(orderId);
        return ResponseEntity.noContent().build(); // Return 204 No Content
    }

}
