package com.netlabs.controller;

import com.netlabs.error.BadArgumentsException;
import com.netlabs.model.Product;
import com.netlabs.model.Purchase;
import com.netlabs.service.ProductService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;

import javax.validation.Valid;
import java.util.Collection;

@RestController
@RequestMapping("/products")
public class ProductsController {

    @Autowired
    ProductService productService;
    
    // Obtener todos los productos.
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<Collection<Product>> getAllProducts() {
		return new ResponseEntity<>(productService.findAll(), HttpStatus.OK);
	}
	
	// Obtener un producto.
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<Product> getProduct(@PathVariable Long id) {
		return new ResponseEntity<>(productService.findOne(id), HttpStatus.OK);
	}
	
	// Obtener las compras de un determinado producto y fecha.
	@RequestMapping(value = "/{id}/purchases", method = RequestMethod.GET)
	public ResponseEntity<Collection<Purchase>> getPurchasesFromProductByDate(@PathVariable Long id, @RequestParam("date") String date) {
		return new ResponseEntity<>(productService.findByIdAndCreatedDate(id, date), HttpStatus.OK);
	}

    // Editar un producto.
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public ResponseEntity<?> updateProduct(@PathVariable Long id, @Valid @RequestBody Product productDetails, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			throw new BadArgumentsException(bindingResult.getFieldErrors());
        }
		
		return ResponseEntity.ok(productService.update(id, productDetails));
    }
	
	// Crear un nuevo producto.
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<?> createProduct(@Valid @RequestBody Product product, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			throw new BadArgumentsException(bindingResult.getFieldErrors());
        }
		
		productService.create(product);
		
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
	public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
		productService.delete(id);
		return new ResponseEntity<Void>(HttpStatus.OK);
	}
}
