package com.netlabs.model;

import javax.persistence.*;
import javax.validation.constraints.Max;
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
	
	@NotBlank(message = "can't be blank")
	@Size(min = 1, max = 64, message = "the size has to be between 1 and 64")
	private String name;
	
	@NotBlank(message = "can't be blank")
	@Size(min = 1, max = 256)
	private String description;

	@NotNull(message = "can't be null")
	@Min(value = 0, message = "it has to be greater than or equal to 0")
    private Integer stock;
	
	@NotNull(message = "can't be null")
	@Min(value = 0, message = "it has to be greater than or equal to 0")
    private Integer price;

	@Min(value = 0, message = "it has to be 1 or 0")
	@Max(value = 1, message = "it has to be 1 or 0")
    private Integer lowStockFlag = 0;
	
	@OneToMany(mappedBy = "product", orphanRemoval=true)
	@JsonIgnore
	private List<Purchase> purchases;
	
	@ManyToOne
	@JoinColumn (name="category_id")
	@NotNull(message = "can't be null")
	private Category category;

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

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Integer getLowStockFlag() {
        return lowStockFlag;
    }

    public void setLowStockFlag(Integer lowStockFlag) {
        this.lowStockFlag = lowStockFlag;
    }

	public List<Purchase> getPurchases() {
		return purchases;
	}

	public void setPurchases(List<Purchase> purchases) {
		this.purchases = purchases;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}
}