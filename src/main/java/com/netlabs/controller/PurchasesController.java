package com.netlabs.controller;

import com.netlabs.model.Product;
import com.netlabs.model.Purchase;
import com.netlabs.repository.ProductsRepository;
import com.netlabs.repository.PurchasesRepository;

import java.util.Collection;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/purchases")
public class PurchasesController {

    @Autowired
    PurchasesRepository purchasesRepository;
    @Autowired
    ProductsRepository productsRepository;
    
	// Get all purchases.
 	@RequestMapping(method = RequestMethod.GET)
 	public ResponseEntity<Collection<Purchase>> getAllPurchases() {
 		return new ResponseEntity<>(purchasesRepository.findAll(), HttpStatus.OK);
 	}
	
	// Get one purchase.
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<Purchase> getPurchase(@PathVariable Long id) {
		Purchase purchase = purchasesRepository.findOne(id);

		if (purchase != null) {
			return new ResponseEntity<>(purchase, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
		}
	}
    
	// Create purchase.
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<Purchase> createPurchase(@Valid @RequestBody Purchase purchase) {
		Product product = productsRepository.findOne(purchase.getProduct().getId());
		product.setStock(product.getStock()-1);
		productsRepository.save(product);
		purchase.setProduct(product);
		return new ResponseEntity<>(purchasesRepository.save(purchase), HttpStatus.CREATED);
	}
	
	// Delete purchase.
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
