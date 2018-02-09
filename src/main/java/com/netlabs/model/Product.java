package com.netlabs.model;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import java.util.List;

import org.hibernate.validator.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "products")
public class Product {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "product_id")
	private Long id;
	
	@NotBlank
	@Size(min = 1, max = 64)
	private String name;
	
	@NotBlank
	@Size(min = 1, max = 256)
	private String description;
	
	@NotNull
	@Min(0)
    private Integer stock;
	
	@NotNull
	@Min(0)
	private Integer lowThresholdStock;
	
	@OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
	@JsonIgnore
	private List<Purchase> purchases;

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

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public Integer getLowThresholdStock() {
        return lowThresholdStock;
    }

    public void setLowThresholdStock(Integer lowThresholdStock) {
        this.lowThresholdStock = lowThresholdStock;
    }

	public List<Purchase> getPurchases() {
		return purchases;
	}

	public void setPurchases(List<Purchase> purchases) {
		this.purchases = purchases;
	}
}