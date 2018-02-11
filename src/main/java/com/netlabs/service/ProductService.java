package com.netlabs.service;

import java.util.Collection;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.netlabs.model.Category;
import com.netlabs.model.Product;
import com.netlabs.model.Purchase;
import com.netlabs.repository.ProductsRepository;

@Service
public class ProductService {
	
	@Autowired
    ProductsRepository productsRepository;
	
	@Autowired
    CategoryService categoryService;
	
	@Autowired
    PurchaseService purchaseService;
    
	public Collection<Product> findAll() {
		return productsRepository.findAll();
	}

	public Product findOne(Long id) {
		Product product = productsRepository.findOne(id);

		if (product != null) {
			return product;
		} else {
			throw new EntityNotFoundException("Product was not found for parameter {id=" + id + "}");
		}
	}

	public Collection<Purchase> findByIdAndCreatedDate(Long id, String date) {
		Product product = productsRepository.findOne(id);
		
        if(product != null) {
        	return purchaseService.findByIdAndCreatedDate(id, date);
        } else {
			throw new EntityNotFoundException("Product was not found for parameter {id=" + id + "}");
        }
	}

    public Product update(Long id, Product productDetails) {
    	Product product = productsRepository.findOne(id);
    	
        if(product == null) {
			throw new EntityNotFoundException("Product was not found for parameter {id=" + id + "}");
        }
        
        Category category = categoryService.findOne(productDetails.getCategory().getId());

        product.setName(productDetails.getName());
        product.setDescription(productDetails.getDescription());
        product.setStock(productDetails.getStock());
        product.setPrice(productDetails.getPrice());
        product.setCategory(category);
        
        if(product.getStock() <= category.getLowThresholdStock())
        	product.setLowStockFlag(1);
        else
        	product.setLowStockFlag(0);
        
        return productsRepository.save(product);
    }

    public Product update(Product product) {
    	return this.update(product.getId(), product);
    }

	public void create(Product product) {
		categoryService.findOne(product.getCategory().getId());		
		productsRepository.save(product);
	}
	
	public void delete(Long id) {
		Product product = productsRepository.findOne(id);
		
		if (product != null) {
			productsRepository.delete(id);
		} else {
			throw new EntityNotFoundException("Product was not found for parameter {id=" + id + "}");
		}
	}
}
