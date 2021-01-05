package com.ivo.javatraing.controller;

import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.ivo.javatraing.exc.ProductNotFoundException;
import com.ivo.javatraing.model.Product;
import com.ivo.javatraing.repo.ProductRepository;

@RestController
public class ProductController {

	@Autowired
	ProductRepository repository;
	
	//Get all products
    @GetMapping("/products")
    public ResponseEntity<List<Product>>  all() {

        List<Product> products = repository.findAll();
        
        return ResponseEntity.status(HttpStatus.OK).body(products);
    }
    
  //Create product
    @PostMapping("/addProduct")
    public ResponseEntity<?> newProduct(@RequestBody Product newProduct) throws URISyntaxException {
    
        repository.save(newProduct);
        return ResponseEntity.status(HttpStatus.OK).body(newProduct);

    }
        
    @DeleteMapping("/delProduct/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable String id) {
        repository.deleteById(Integer.parseInt(id));

        return ResponseEntity.noContent().build();
    }
    
  //Get one product
    @GetMapping("/products/{id}")
    public ResponseEntity<Product> one(@PathVariable String id) {

        Product product = repository.findById(Integer.parseInt(id))
                .orElseThrow(() -> new ProductNotFoundException(id));
        return ResponseEntity.status(HttpStatus.OK).body(product);
    }
    
  //Update product
    @PutMapping("/updProduct")
    public ResponseEntity<?> updateProduct(@RequestBody Product product) throws URISyntaxException {
    	boolean flag=true;
    	 Optional<Product> product1 = repository.findById(product.getId());
    	 if (!product1.isEmpty()) {
    		 repository.save(product);
    	 		return ResponseEntity.status(HttpStatus.OK).body(product);
    	 }
    	 else {
    		 	
    	 		return ResponseEntity.notFound().build();
    	 }
        

    }
}
