package com.netlabs.repository;

import java.util.Collection;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.netlabs.model.Category;

@Repository
public interface CategoriesRepository extends CrudRepository<Category, Long> {
	
	Collection<Category> findAll();

}