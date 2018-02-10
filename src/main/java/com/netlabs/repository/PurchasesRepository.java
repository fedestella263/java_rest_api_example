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
	
	@Query(value = "SELECT * FROM products.purchases WHERE product_id =:id AND DATE(date) = :date", nativeQuery = true) 
	Collection<Purchase> findByIdAndCreatedDate(@Param("id") Long id, @Param("date") String date);
}