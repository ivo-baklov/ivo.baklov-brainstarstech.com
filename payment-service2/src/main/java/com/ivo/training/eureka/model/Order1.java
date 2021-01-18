package com.ivo.training.eureka.model;

import java.io.Serializable;
import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
public class Order1 implements Serializable {
	@Id
	int id;

	@Column(name = "product_id")
	@JsonProperty("productId")
	@NotNull(message = "Product ID can not be null.")
	@NotEmpty(message = "Product ID can not be empty.")
	String productId;

	@JsonProperty("product")
	@NotNull(message = "Product must be relate to category.")
	@NotEmpty(message = "Product category can not be empty.")
	String product;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public LocalDate getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(LocalDate createdDate) {
		this.createdDate = createdDate;
		;
	}

	int quantity;

	@Column(name = "created_date")
	@JsonProperty("createdDate")
	@JsonFormat(pattern = "dd-MM-yyyy")
	private LocalDate createdDate;

	@Column(name = "last_modified_date")
	@JsonProperty("lastModifiedDate")
	@JsonFormat(pattern = "dd-MM-yyyy")
	private LocalDate lastModifiedDate;

	public LocalDate getLastModifiedDate() {
		return lastModifiedDate;
	}

	public void setLastModifiedDate(LocalDate lastModifiedDate) {
		this.lastModifiedDate = lastModifiedDate;
	}

}
