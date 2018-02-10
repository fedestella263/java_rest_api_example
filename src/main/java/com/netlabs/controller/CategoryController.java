package com.netlabs.controller;

import com.netlabs.model.Category;
import com.netlabs.model.Product;
import com.netlabs.repository.CategoriesRepository;
import com.netlabs.repository.ProductsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import javax.validation.Valid;
import java.util.Collection;
import java.util.List;

@RestController
@RequestMapping("/categories")
public class CategoryController {

    @Autowired
    CategoriesRepository categoriesRepository;
    @Autowired
    ProductsRepository productsRepository;
    
    // Obtener todas las categorías.
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<Collection<Category>> getAllCategories() {
		return new ResponseEntity<>(categoriesRepository.findAll(), HttpStatus.OK);
	}
	
	// Obtener una categoría.
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<Category> getCategory(@PathVariable Long id) {
		Category category = categoriesRepository.findOne(id);

		if (category != null) {
			return new ResponseEntity<>(category, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
		}
	}

    // Editar una categoría.
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public ResponseEntity<Category> updateCategory(@PathVariable(value = "id") Long categoryId, @RequestBody Category categoryDetails) {
        Category category = categoriesRepository.findOne(categoryId);
        if(category == null) {
			return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }

        category.setLowThresholdStock(categoryDetails.getLowThresholdStock());
        
        // Obtiene todos los productos y actualiza la bandera de bajo stock.
        List<Product> products = category.getProducts();
        for(Product product : products) {
    		product.setLowStockFlag((product.getStock() <= category.getLowThresholdStock() ? 1 : 0));
    		productsRepository.save(product);
        }

        Category updatedCategory = categoriesRepository.save(category);
        return ResponseEntity.ok(updatedCategory);
    }
	
	// Crear una nueva categoría.
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<?> createCategory(@Valid @RequestBody Category category) {
		
		categoriesRepository.save(category);
		
		// Retorna en header - location la URI hacia la nueva categoría.
	    HttpHeaders httpHeaders = new HttpHeaders();
	    httpHeaders.setLocation(ServletUriComponentsBuilder
            .fromCurrentRequest()
            .path("/{id}")
            .buildAndExpand(category.getId())
            .toUri());
	    
		return new ResponseEntity<>(httpHeaders, HttpStatus.CREATED);
	}
	
	// Eliminar una categoría.
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Void> deleteCategory(@PathVariable(value = "id") Long categoryId) {
		Category category = categoriesRepository.findOne(categoryId);
		
		if (category != null) {
			categoriesRepository.delete(categoryId);
			return new ResponseEntity<Void>(HttpStatus.OK);
		} else {
			return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
		}
	}
}
