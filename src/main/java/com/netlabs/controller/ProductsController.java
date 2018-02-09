package com.netlabs.controller;

import com.netlabs.model.Product;
import com.netlabs.repository.ProductsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import javax.validation.Valid;

import java.util.Collection;


@RestController
@RequestMapping("/products")
public class ProductsController {

    @Autowired
    ProductsRepository productsRepository;
    
    // Get all products.
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<Collection<Product>> getAllProducts() {
		return new ResponseEntity<>(productsRepository.findAll(), HttpStatus.OK);
	}
	
	// Get one product.
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<Product> getProduct(@PathVariable Long id) {
		Product product = productsRepository.findOne(id);

		if (product != null) {
			return new ResponseEntity<>(product, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
		}
	}

    // Update product.
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public ResponseEntity<Product> updateNote(@PathVariable(value = "id") Long productId, @Valid @RequestBody Product productDetails) {
        Product product = productsRepository.findOne(productId);
        if(product == null) {
			return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }

        product.setName(productDetails.getName());
        product.setDescription(productDetails.getDescription());
        product.setStock(productDetails.getStock());
        product.setLowThresholdStock(productDetails.getLowThresholdStock());

        Product updatedProduct = productsRepository.save(product);
        return ResponseEntity.ok(updatedProduct);
    }	
}
