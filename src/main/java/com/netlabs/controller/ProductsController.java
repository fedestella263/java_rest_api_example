package com.netlabs.controller;

import com.netlabs.error.ApiError;
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
import org.springframework.validation.BindingResult;

import javax.persistence.EntityNotFoundException;
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
			throw new EntityNotFoundException("Product was not found for parameter {id=" + id + "}");
		}
	}
	
	// Obtener las compras de un determinado producto y fecha.
	@RequestMapping(value = "/{id}/purchases", method = RequestMethod.GET)
	public ResponseEntity<Collection<Purchase>> getPurchasesFromProductByDate(@PathVariable Long id, @RequestParam("date") String date) {
		Product product = productsRepository.findOne(id);
        if(product != null) {
        	return new ResponseEntity<>(purchasesRepository.findByIdAndCreatedDate(id, date), HttpStatus.OK);
        } else {
			throw new EntityNotFoundException("Product was not found for parameter {id=" + id + "}");
        }
	}

    // Editar un producto.
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public ResponseEntity<?> updateProduct(@PathVariable Long id, @Valid @RequestBody Product productDetails, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
    		return new ResponseEntity<>(new ApiError(HttpStatus.BAD_REQUEST, "Bad arguments", bindingResult.getFieldErrors()), HttpStatus.BAD_REQUEST);
        }
		
        Product product = productsRepository.findOne(id);
        if(product == null) {
			throw new EntityNotFoundException("Product was not found for parameter {id=" + id + "}");
        }

        product.setName(productDetails.getName());
        product.setDescription(productDetails.getDescription());
        product.setStock(productDetails.getStock());
        product.setPrice(productDetails.getPrice());
        product.setCategory(productDetails.getCategory());
        
        return ResponseEntity.ok(productsRepository.save(product));
    }
	
	// Crear un nuevo producto.
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<?> createProduct(@Valid @RequestBody Product product, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
    		return new ResponseEntity<>(new ApiError(HttpStatus.BAD_REQUEST, "Bad arguments", bindingResult.getFieldErrors()), HttpStatus.BAD_REQUEST);
        }
		
		if(productsRepository.findOne(product.getCategory().getId()) == null)
			throw new EntityNotFoundException("Category was not found for parameter {id=" + product.getCategory().getId() + "}");
		
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
	public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
		Product product = productsRepository.findOne(id);
		
		if (product != null) {
			productsRepository.delete(id);
			return new ResponseEntity<Void>(HttpStatus.OK);
		} else {
			throw new EntityNotFoundException("Product was not found for parameter {id=" + id + "}");
		}
	}
}
