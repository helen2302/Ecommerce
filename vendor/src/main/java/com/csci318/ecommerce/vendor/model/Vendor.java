package com.csci318.ecommerce.vendor.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="Vendor")

public class Vendor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String name;

    @Column
    private String address;

    @Column
    @OneToMany(mappedBy = "vendor", cascade=CascadeType.ALL,fetch = FetchType.EAGER)
    @JsonIgnore
    private List<Product> products = new ArrayList<>();  // Renamed for clarity

    // Default constructor
    public Vendor() {
    }

    // Parameterized constructor
    public Vendor(String name, String address, List<Product> products) {
        this.name = name;
        this.address = address;
        this.products = products;
    }

    // Getter and Setter for id
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    // Getter and Setter for name
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    // Getter and Setter for productIds
    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void addProducts(Product product){
        products.add(product);
    }
    // toString method to print Vendor details
    @Override
    public String toString() {
        int productQuantity = (products != null) ? products.size() : 0;
        return "Vendor{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", productQuantity=" + productQuantity +
                '}';
    }
}

