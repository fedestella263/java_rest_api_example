package com.netlabs.controller;

import com.netlabs.model.Product;
import com.netlabs.model.Purchase;
import com.netlabs.repository.ProductsRepository;
import com.netlabs.repository.PurchasesRepository;

import java.util.Collection;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequestMapping("/purchases")
public class PurchasesController {

    @Autowired
    PurchasesRepository purchasesRepository;
    @Autowired
    ProductsRepository productsRepository;
    
	// Obtener todas las compras.
 	@RequestMapping(method = RequestMethod.GET)
 	public ResponseEntity<Collection<Purchase>> getAllPurchases() {
 		return new ResponseEntity<>(purchasesRepository.findAll(), HttpStatus.OK);
 	}
	
	// Obtener una compra.
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<Purchase> getPurchase(@PathVariable Long id) {
		Purchase purchase = purchasesRepository.findOne(id);

		if (purchase != null) {
			return new ResponseEntity<>(purchase, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
		}
	}
    
	// Crear una nueva compra.
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<?> createPurchase(@Valid @RequestBody Purchase purchase) {
		
		purchasesRepository.save(purchase);
		Product product = productsRepository.findOne(purchase.getProduct().getId());
		product.setStock(product.getStock()-purchase.getAmount());
		productsRepository.save(product);		

		// Retorna en header - location la URI hacia la nueva compra.
	    HttpHeaders httpHeaders = new HttpHeaders();
	    httpHeaders.setLocation(ServletUriComponentsBuilder
            .fromCurrentRequest()
            .path("/{id}")
            .buildAndExpand(purchase.getId())
            .toUri());
	 
	    return new ResponseEntity<>(httpHeaders, HttpStatus.CREATED);
	}
	
	// Eliminar una compra.
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Void> deletePurchase(@PathVariable(value = "id") Long purchaseId) {
		Purchase purchase = purchasesRepository.findOne(purchaseId);
		
		if (purchase != null) {
			purchasesRepository.delete(purchaseId);
			return new ResponseEntity<Void>(HttpStatus.OK);
		} else {
			return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
		}
	}
}
