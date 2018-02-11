package com.netlabs.repository;

import java.util.Collection;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import com.netlabs.model.Product;

@Repository()
public interface ProductsRepository extends CrudRepository<Product, Long> {
	
	Collection<Product> findAll();

}