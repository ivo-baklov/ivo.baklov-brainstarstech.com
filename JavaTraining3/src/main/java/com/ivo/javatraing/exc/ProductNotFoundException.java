package com.ivo.javatraing.exc;

public class ProductNotFoundException extends RuntimeException {

	public ProductNotFoundException(String id) {
		super("Could not find product with this id: " + id);
	}
}
