package com.netlabs.model;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "products")
public class Product implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;
    private String description;
    private int stock;
    private int lowThresholdStock;

    private Product() { } // JPA only

    public Product(final String name, final String description, final int stock, final int lowThresholdStock) {
        this.name = name;
        this.description = description;
        this.stock = stock;
        this.lowThresholdStock = lowThresholdStock;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
    	return name;
    }

    public String getDescription() {
        return description;
    }

    public int getStock() {
        return stock;
    }

    public int getLowThresholdStock() {
        return lowThresholdStock;
    }
}