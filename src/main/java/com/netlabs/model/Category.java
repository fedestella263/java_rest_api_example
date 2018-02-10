package com.netlabs.model;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.List;


@Entity
@Table(name = "categories")
public class Category {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "category_id")
	private Long id;
	
	@NotNull
	@Min(0)
	private Integer lowThresholdStock;
	
	@OneToMany(mappedBy = "category", orphanRemoval=true)
	@JsonIgnore
	private List<Product> products;

    public Long getId() {
        return id;
    }

    public Integer getLowThresholdStock() {
        return lowThresholdStock;
    }

    public void setLowThresholdStock(Integer lowThresholdStock) {
        this.lowThresholdStock = lowThresholdStock;
    }

	public List<Product> getProducts() {
		return products;
	}

	public void setProducts(List<Product> products) {
		this.products = products;
	}
}