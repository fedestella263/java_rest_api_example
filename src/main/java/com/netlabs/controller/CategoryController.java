package com.netlabs.controller;

import com.netlabs.error.BadArgumentsException;
import com.netlabs.model.Category;
import com.netlabs.service.CategoryService;

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
@RequestMapping("/categories")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;
    
    // Obtener todas las categorías.
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<Collection<Category>> getAllCategories() {
		return new ResponseEntity<>(categoryService.findAll(), HttpStatus.OK);
	}
	
	// Obtener una categoría.
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<Category> getCategory(@PathVariable Long id) {
		return new ResponseEntity<>(categoryService.findOne(id), HttpStatus.OK);
	}

    // Editar una categoría.
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public ResponseEntity<?> updateCategory(@PathVariable Long id, @Valid @RequestBody Category categoryDetails, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			throw new BadArgumentsException(bindingResult.getFieldErrors());
        }
 		
		return new ResponseEntity<>(categoryService.update(id, categoryDetails), HttpStatus.OK);
    }
	
	// Crear una nueva categoría.
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<?> createCategory(@Valid @RequestBody Category category, BindingResult bindingResult) {
 		if (bindingResult.hasErrors()) {
			throw new BadArgumentsException(bindingResult.getFieldErrors());
        }
 		
 		categoryService.create(category);

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
	public ResponseEntity<Void> deleteCategory(@PathVariable Long id) {
		categoryService.delete(id);
		return new ResponseEntity<>(HttpStatus.OK);
	}
}
