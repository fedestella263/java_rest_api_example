package com.netlabs.controllers;

import com.netlabs.model.Product;
import com.netlabs.repository.ProductsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;


@RestController
@RequestMapping("/api/v1")
public class ProductsController {

    @Autowired
    ProductsRepository productsRepository;

    // Get All Products
    @GetMapping("/products")
    public List<Product> getAllNotes() {
        return productsRepository.findAll();
    }

    // Get one Product

    // Update Product

    // Delete Product		
}
