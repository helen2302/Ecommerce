package com.csci318.ecommerce.shipment.model;



import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.util.Set;

@Entity
@Data
public class Shipment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private Long orderId;
    @Column
    private Long customerId;


    public Shipment(){}
    public Shipment(Long orderId,Long customerId) {
        this.orderId = orderId;
        this.customerId = customerId;
    }

    @Override
    public String toString() {
        return "Shipment{" +
                "id=" + id +
                ", orderId=" + orderId +
                ", customerId=" + customerId  +
                '}';
    }

}

