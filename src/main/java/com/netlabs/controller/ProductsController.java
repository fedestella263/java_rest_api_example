package com.netlabs.controller;

import com.netlabs.model.Product;
import com.netlabs.model.Purchase;
import com.netlabs.repository.ProductsRepository;
import com.netlabs.repository.PurchasesRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import javax.validation.Valid;

import java.util.Collection;

@RestController
@RequestMapping("/products")
public class ProductsController {

    @Autowired
    ProductsRepository productsRepository;
    @Autowired
    PurchasesRepository purchasesRepository;
    
    // Obtener todos los productos.
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<Collection<Product>> getAllProducts() {
		return new ResponseEntity<>(productsRepository.findAll(), HttpStatus.OK);
	}
	
	// Obtener un producto.
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<Product> getProduct(@PathVariable Long id) {
		Product product = productsRepository.findOne(id);

		if (product != null) {
			return new ResponseEntity<>(product, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
		}
	}
	
	// Obtener las compras de un determinado producto y fecha.
	@RequestMapping(value = "/{id}/purchases", method = RequestMethod.GET)
	public ResponseEntity<Collection<Purchase>> getPurchasesFromProductByDate(@RequestParam("createdDate") String createdDate) {
 		return new ResponseEntity<>(purchasesRepository.findByCreatedDate(createdDate), HttpStatus.OK);
	}

    // Editar un producto.
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public ResponseEntity<Product> updateProduct(@PathVariable(value = "id") Long productId, @RequestBody Product productDetails) {
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
	
	// Crear un nuevo producto.
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<?> createProduct(@Valid @RequestBody Product product) {
		
		productsRepository.save(product);
		
		// Retorna en header - location la URI hacia el nuevo producto.
	    HttpHeaders httpHeaders = new HttpHeaders();
	    httpHeaders.setLocation(ServletUriComponentsBuilder
            .fromCurrentRequest()
            .path("/{id}")
            .buildAndExpand(product.getId())
            .toUri());
	    
		return new ResponseEntity<>(httpHeaders, HttpStatus.CREATED);
	}
	
	// Eliminar un producto.
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Void> deleteProduct(@PathVariable(value = "id") Long productId) {
		Product product = productsRepository.findOne(productId);
		
		if (product != null) {
			productsRepository.delete(productId);
			return new ResponseEntity<Void>(HttpStatus.OK);
		} else {
			return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
		}
	}
}
