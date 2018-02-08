package com.netlabs.controllers;

import com.netlabs.model.Product;
import com.netlabs.repository.ProductsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import javax.validation.Valid;
import java.util.List;


@RestController
@RequestMapping("/api/v1")
public class ProductsController {

    @Autowired
    ProductsRepository productsRepository;

    // Get All Products.
    @GetMapping("/products")
    public List<Product> getAllNotes() {
        return productsRepository.findAll();
    }

    // Get one Product.

    // Update Product.
    @PutMapping("/products/{id}")
    public ResponseEntity<Product> updateNote(@PathVariable(value = "id") Long productId, @Valid @RequestBody Product productDetails) {
        Product product = productsRepository.findOne(productId);
        if(product == null) {
            return ResponseEntity.notFound().build();
        }

        product.setName(productDetails.getName());
        product.setDescription(productDetails.getDescription());
        product.setStock(productDetails.getStock());
        product.setLowThresholdStock(productDetails.getLowThresholdStock());

        Product updatedProduct = productsRepository.save(product);
        return ResponseEntity.ok(updatedProduct);
    }

    // Delete Product.		
}
