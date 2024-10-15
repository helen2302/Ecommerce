package com.csci318.ecommerce.order.model;

import com.csci318.ecommerce.order.model.Enum.Constants;
import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@Table(name = "\"order\"")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Relation with customer
    private Long customerId;
    // Use ManyToMany for a many-to-many relationship
    @ElementCollection
    private List<Long> productIds;
    // List of products in the order

    @Column
    private Double totalAmount;
    @Column
    private LocalDateTime creationDate;

    @Column
    private String message;
    @Enumerated(EnumType.STRING)
    @Column

    private Constants.OrderStatus orderStatus;
    public Order() {}

    public Order(Long customerId) {
        this.customerId = customerId;
        this.creationDate = LocalDateTime.now();
        this.orderStatus = Constants.OrderStatus.PENDING;
        this.totalAmount = 0.0;
        this.productIds = new ArrayList<>();
        message="";
    }



    //add product and set price
    public void addProducts(Long productId){
        productIds.add(productId);
    }

    @Override
    public String toString() {
        String productsString = "";
        for (Long productId : productIds) {
            productsString += productId + ", ";
        }
        // Remove the last comma and space if the productIds list is not empty
        if (!productsString.isEmpty()) {
            productsString = productsString.substring(0, productsString.length() - 2);
        }

        return "Order { " +
                "id=" + id +
                ", customerId=" + customerId +
                ", productIds=[" + productsString + "]" +
                ", totalAmount=" + totalAmount +
                ", message='" + message + '\'' +
                " }";
    }


}
