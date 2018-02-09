package com.netlabs.model;

import javax.persistence.*;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;
import javax.validation.constraints.Digits;

@Entity
@Table(name = "products")
public class Product {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "product_id")
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