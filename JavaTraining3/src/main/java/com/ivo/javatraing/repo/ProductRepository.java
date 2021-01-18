package com.ivo.javatraing.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.ivo.javatraing.model.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer>   {

	
	@Query(nativeQuery = true, value="select * from product ?0 ")
	
	List<Product> findMyFiltersAll(String myQuery);
	
	 Page<Product> findAll( Pageable pageable);
	
}
