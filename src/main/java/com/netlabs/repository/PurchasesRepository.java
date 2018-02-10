package com.netlabs.repository;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.netlabs.model.Purchase;

@Repository
public interface PurchasesRepository extends CrudRepository<Purchase, Long> {
	
	Collection<Purchase> findAll();
	
	@Query(value = "SELECT * FROM products.purchases WHERE DATE(created_date) = :createdDate", nativeQuery = true) 
	Collection<Purchase> findByCreatedDate(@Param("createdDate") String createdDate);
}