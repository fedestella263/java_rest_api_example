package com.netlabs.repository;

import java.util.Collection;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.netlabs.model.Purchase;

@Repository
public interface PurchasesRepository extends CrudRepository<Purchase, Long> {
	
	Collection<Purchase> findAll();

}