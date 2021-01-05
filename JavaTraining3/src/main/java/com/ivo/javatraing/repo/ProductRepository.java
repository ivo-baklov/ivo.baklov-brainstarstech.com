package com.ivo.javatraing.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ivo.javatraing.model.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer>   {
	
}
