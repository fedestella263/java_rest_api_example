package com.netlabs.model;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import javax.persistence.*;
import javax.validation.constraints.Digits;

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

    @NotEmpty
    @Digits(integer=10, fraction=0)
    private String stock;
    
    @NotEmpty
    @Digits(integer=10, fraction=0)
    private String lowThresholdStock;

    private Product() { } // JPA only

    public Product(final String name, final String description, final String stock, final String lowThresholdStock) {
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

    public String getStock() {
        return stock;
    }

    public void setStock(String stock) {
        this.stock = stock;
    }

    public String getLowThresholdStock() {
        return lowThresholdStock;
    }

    public void setLowThresholdStock(String lowThresholdStock) {
        this.lowThresholdStock = lowThresholdStock;
    }
}