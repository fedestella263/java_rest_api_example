package com.netlabs.service;

import java.util.Collection;
import java.util.List;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.netlabs.model.Category;
import com.netlabs.model.Product;
import com.netlabs.repository.CategoriesRepository;

@Service
public class CategoryService {

    @Autowired
    CategoriesRepository categoriesRepository;

    @Autowired
    ProductService productService;
    
 	public Collection<Category> findAll() {
		return categoriesRepository.findAll();
 	}

 	public Category findOne(Long id) {
 		Category category = categoriesRepository.findOne(id);
 		
		if (category != null) {
			return category;
		} else {
			throw new EntityNotFoundException("Category was not found for parameter {id=" + id + "}");
		}
 	}

 	public Category update(Long id, Category categoryDetails) {		
        Category category = categoriesRepository.findOne(id);
        if(category == null) {
			throw new EntityNotFoundException("Category was not found for parameter {id=" + id + "}");
        }

        category.setLowThresholdStock(categoryDetails.getLowThresholdStock());
        
        // Obtiene todos los productos y actualiza la bandera de bajo stock.
        List<Product> products = category.getProducts();
        for(Product product : products) {
    		product.setLowStockFlag((product.getStock() <= category.getLowThresholdStock() ? 1 : 0));
    		productService.update(product.getId(), product);
        }

        return categoriesRepository.save(category);
 	}

 	public Category update(Category category) {		
 		return this.update(category.getId(), category);
 	}

 	public Category create(Category category) {
		return categoriesRepository.save(category);
 	}

 	public void delete(Long id) {
		Category category = categoriesRepository.findOne(id);
		
		if (category != null) {
			categoriesRepository.delete(id);
		} else {
			throw new EntityNotFoundException("Category was not found for parameter {id=" + id + "}");
		}
 	}
}
