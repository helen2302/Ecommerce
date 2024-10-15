package com.csci318.ecommerce.customer.model;


import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String name;

    @Column
    private String email;

    @Column
    private String address;

    @Column
    private String password;

    @Embedded
    private CardDetails cardDetails;

    // Default constructor is needed by JPA
    public Customer() {}

    // Constructor without id, as id is generated automatically
    public Customer(String name, String email, String address, String password, CardDetails cardDetails) {
        this.name = name;
        this.email = email;
        this.address = address;
        this.password = password;
        this.cardDetails = cardDetails;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", address='" + address + '\'' +
                ", password='" + password + '\'' +
                ", cardDetails=" + cardDetails.toString() + // Include cardDetails in the output
                '}';
    }
}