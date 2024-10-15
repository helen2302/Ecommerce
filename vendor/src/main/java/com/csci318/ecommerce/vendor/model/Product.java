package com.csci318.ecommerce.vendor.model;


import jakarta.persistence.*;
import lombok.Data;

@Table(name="Product")
@Entity
@Data
public class Product {

    @Id
    @Column(name="productId")
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long id;
    @Column
    private String name;
    @Column
    private Double price;
    @Column
    private Long inStock;

    @ManyToOne
    @JoinColumn(name = "vendor")
    private Vendor vendor;
    // Getters and Setters

    public Product() {}

    public Product(String name, Double price,Long inStock, Vendor vendor) {
        this.name = name;
        this.price = price;
        this.inStock = inStock;
        this.vendor = vendor;
    }


    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", inStock=" + inStock +
                '}';
    }
}

