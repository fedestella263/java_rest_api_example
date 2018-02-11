package com.netlabs.controller;

import com.netlabs.error.ApiError;
import com.netlabs.model.Product;
import com.netlabs.model.Purchase;
import com.netlabs.repository.ProductsRepository;
import com.netlabs.repository.PurchasesRepository;

import java.util.Collection;

import javax.persistence.EntityNotFoundException;
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
			throw new EntityNotFoundException("Purchase was not found for parameter {id=" + id + "}");
		}
	}

    // Editar una compra.
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public ResponseEntity<?> updateCategory(@PathVariable Long id, @Valid @RequestBody Purchase purchaseDetails, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
    		return new ResponseEntity<>(new ApiError(HttpStatus.BAD_REQUEST, "Bad arguments", bindingResult.getFieldErrors()), HttpStatus.BAD_REQUEST);
        }
		
        Purchase purchase = purchasesRepository.findOne(id);
        if(purchase == null) {
			throw new EntityNotFoundException("Purchase was not found for parameter {id=" + id + "}");
        }
        
        // Diferente producto.
        if(purchase.getProduct().getId() != purchaseDetails.getProduct().getId()) {
        	Integer oldAmount = purchase.getAmount();
        	Product oldProduct = productsRepository.findOne(purchase.getProduct().getId());
        	Product newProduct = productsRepository.findOne(purchaseDetails.getProduct().getId());
    		
        	// Validaciones para el nuevo producto.
    		if(newProduct == null)
    			throw new EntityNotFoundException("Product was not found for parameter {id=" + purchase.getProduct().getId() + "}");
    		
        	if(purchaseDetails.getAmount() > newProduct.getStock())
    			return new ResponseEntity<>(new ApiError(HttpStatus.BAD_REQUEST, "The available stock in the new product is " + newProduct.getStock()), HttpStatus.BAD_REQUEST);
        	
        	newProduct.setStock(newProduct.getStock()-purchaseDetails.getAmount());    		
    		if(newProduct.getStock() <= newProduct.getCategory().getLowThresholdStock())
    			newProduct.setLowStockFlag(1);
    		
    		productsRepository.save(newProduct);	
        	
        	// Se guarda el producto.
        	purchase.setProduct(newProduct);
        	
        	// Se retorna el stock del antiguo producto.
        	oldProduct.setStock(oldProduct.getStock() + oldAmount);
			
			if(oldProduct.getStock() > oldProduct.getCategory().getLowThresholdStock())
				oldProduct.setLowStockFlag(0);
			
			productsRepository.save(oldProduct);

		// Se mantiene el producto.
        } else {
        	purchase.getProduct().setStock(purchase.getProduct().getStock() - (purchaseDetails.getAmount()-purchase.getAmount()));

			if(purchase.getProduct().getStock() > purchase.getProduct().getCategory().getLowThresholdStock())
				purchase.getProduct().setLowStockFlag(0);
			else
				purchase.getProduct().setLowStockFlag(1);
			
			productsRepository.save(purchase.getProduct());
        }
        
        purchase.setAmount(purchaseDetails.getAmount());
        return ResponseEntity.ok(purchasesRepository.save(purchase));
    }
    
	// Crear una nueva compra.
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<?> createPurchase(@Valid @RequestBody Purchase purchase, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
    		return new ResponseEntity<>(new ApiError(HttpStatus.BAD_REQUEST, "Bad arguments", bindingResult.getFieldErrors()), HttpStatus.BAD_REQUEST);
        }
		
		Product product = productsRepository.findOne(purchase.getProduct().getId());
		
		if(product == null)
			throw new EntityNotFoundException("Product was not found for parameter {id=" + purchase.getProduct().getId() + "}");
		
		if(purchase.getAmount() > product.getStock())
			return new ResponseEntity<>(new ApiError(HttpStatus.BAD_REQUEST, "The available stock is " + product.getStock()), HttpStatus.BAD_REQUEST);
		
		purchase.setProduct(product);
		purchasesRepository.save(purchase);
		product.setStock(product.getStock()-purchase.getAmount());
		
		if(product.getStock() <= product.getCategory().getLowThresholdStock())
			product.setLowStockFlag(1);
		
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
	public ResponseEntity<Void> deletePurchase(@PathVariable Long id) {
		Purchase purchase = purchasesRepository.findOne(id);
		
		if (purchase != null) {
			Product product = productsRepository.findOne(purchase.getProduct().getId());
			product.setStock(product.getStock() + purchase.getAmount());
			
			if(product.getStock() > product.getCategory().getLowThresholdStock())
				product.setLowStockFlag(0);
			
			productsRepository.save(product);
			purchasesRepository.delete(id);
			return new ResponseEntity<Void>(HttpStatus.OK);
		} else {
			throw new EntityNotFoundException("Purchase was not found for parameter {id=" + id + "}");
		}
	}
}
