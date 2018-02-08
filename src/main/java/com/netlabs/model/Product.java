package com.netlabs.model;

import org.hibernate.validator.constraints.NotBlank;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "products")
@EntityListeners(AuditingEntityListener.class)
public class Product implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    @NotBlank
    private String name;
    @NotBlank
    private String description;
    @Column(nullable = false)
    private int stock;
    @Column(nullable = false)
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

    public void setName(String name) {
    	this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public int getLowThresholdStock() {
        return lowThresholdStock;
    }

    public void setLowThresholdStock(int lowThresholdStock) {
        this.lowThresholdStock = lowThresholdStock;
    }
}