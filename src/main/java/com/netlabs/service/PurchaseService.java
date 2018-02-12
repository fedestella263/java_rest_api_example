package com.netlabs.service;

import java.util.Collection;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.netlabs.error.NoStockAvailableException;
import com.netlabs.model.Product;
import com.netlabs.model.Purchase;
import com.netlabs.repository.PurchasesRepository;

@Service
public class PurchaseService {

    @Autowired
    PurchasesRepository purchasesRepository;

    @Autowired
    ProductService productService;

 	public Collection<Purchase> findAll() {
 		return purchasesRepository.findAll();
 	}

	public Purchase findOne(Long id) {
		Purchase purchase = purchasesRepository.findOne(id);

		if (purchase != null) {
			return purchase;
		} else {
			throw new EntityNotFoundException("Purchase was not found for parameter {id=" + id + "}");
		}
	}
	
	public Collection<Purchase> findByIdAndCreatedDate(Long id, String date) {
		return purchasesRepository.findByIdAndCreatedDate(id, date);
	}

    public Purchase update(Long id, Purchase purchaseDetails) {		
        Purchase purchase = purchasesRepository.findOne(id);
        if(purchase == null) {
			throw new EntityNotFoundException("Purchase was not found for parameter {id=" + id + "}");
        }
        
        // Diferente producto.
        if(purchase.getProduct().getId() != purchaseDetails.getProduct().getId()) {
        	Integer oldAmount = purchase.getAmount();
        	Product oldProduct = productService.findOne(purchase.getProduct().getId());
        	Product newProduct = productService.findOne(purchaseDetails.getProduct().getId());
    		
        	// Validaciones para el nuevo producto.
        	if(purchaseDetails.getAmount() > newProduct.getStock())
        		throw new NoStockAvailableException(newProduct.getStock());   
    		
        	newProduct.setStock(newProduct.getStock()-purchaseDetails.getAmount());    		
    		if(newProduct.getStock() < newProduct.getCategory().getLowThresholdStock())
    			newProduct.setLowStockFlag(1);
    		
    		productService.update(newProduct);	
        	
        	// Se guarda el producto.
        	purchase.setProduct(newProduct);
        	
        	// Se retorna el stock del antiguo producto.
        	oldProduct.setStock(oldProduct.getStock() + oldAmount);
			
			if(oldProduct.getStock() >= oldProduct.getCategory().getLowThresholdStock())
				oldProduct.setLowStockFlag(0);
			
			productService.update(oldProduct);

		// Se mantiene el producto.
        } else {
        	purchase.getProduct().setStock(purchase.getProduct().getStock() - (purchaseDetails.getAmount()-purchase.getAmount()));

			if(purchase.getProduct().getStock() >= purchase.getProduct().getCategory().getLowThresholdStock())
				purchase.getProduct().setLowStockFlag(0);
			else
				purchase.getProduct().setLowStockFlag(1);
			
			productService.update(purchase.getProduct());
        }
        
        purchase.setAmount(purchaseDetails.getAmount());
        return purchasesRepository.save(purchase);
    }

    public Purchase update(Purchase purchase) {		
        return this.update(purchase.getId(), purchase);
    }

	public void create(Purchase purchase) {
		
		Product product = productService.findOne(purchase.getProduct().getId());
		
		if(product == null)
			throw new EntityNotFoundException("Product was not found for parameter {id=" + purchase.getProduct().getId() + "}");
		
		if(purchase.getAmount() > product.getStock())
    		throw new NoStockAvailableException(product.getStock());   
		
		purchase.setProduct(product);
		purchasesRepository.save(purchase);
		product.setStock(product.getStock()-purchase.getAmount());
		
		if(product.getStock() < product.getCategory().getLowThresholdStock())
			product.setLowStockFlag(1);
		
		productService.update(product);	
	}

	public void delete(Long id) {
		Purchase purchase = purchasesRepository.findOne(id);
		
		if (purchase != null) {
			Product product = productService.findOne(purchase.getProduct().getId());
			product.setStock(product.getStock() + purchase.getAmount());
			
			if(product.getStock() >= product.getCategory().getLowThresholdStock())
				product.setLowStockFlag(0);
			
			productService.update(product);
			purchasesRepository.delete(id);
		} else {
			throw new EntityNotFoundException("Purchase was not found for parameter {id=" + id + "}");
		}
	}
}
