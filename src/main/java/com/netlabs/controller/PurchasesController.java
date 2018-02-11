package com.netlabs.controller;

import com.netlabs.error.BadArgumentsException;
import com.netlabs.model.Purchase;
import com.netlabs.service.PurchaseService;

import java.util.Collection;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequestMapping("/purchases")
public class PurchasesController {

    @Autowired
    PurchaseService purchaseService;
    
	// Obtener todas las compras.
 	@RequestMapping(method = RequestMethod.GET)
 	public ResponseEntity<Collection<Purchase>> getAllPurchases() {
 		return new ResponseEntity<>(purchaseService.findAll(), HttpStatus.OK);
 	}
	
	// Obtener una compra.
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<Purchase> getPurchase(@PathVariable Long id) {
		return new ResponseEntity<>(purchaseService.findOne(id), HttpStatus.OK);	
	}

    // Editar una compra.
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public ResponseEntity<?> updateCategory(@PathVariable Long id, @Valid @RequestBody Purchase purchaseDetails, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			throw new BadArgumentsException(bindingResult.getFieldErrors());
        }
		
        return ResponseEntity.ok(purchaseService.update(id, purchaseDetails));
    }
    
	// Crear una nueva compra.
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<?> createPurchase(@Valid @RequestBody Purchase purchase, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			throw new BadArgumentsException(bindingResult.getFieldErrors());
        }
		
		purchaseService.create(purchase);		

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
	public ResponseEntity<Void> deletePurchase(@PathVariable Long id) {
		purchaseService.delete(id);
		return new ResponseEntity<>(HttpStatus.OK);
	}
}
