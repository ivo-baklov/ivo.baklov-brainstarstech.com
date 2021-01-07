package com.ivo.javatraing.model;
import java.io.Serializable;
import java.sql.Date;
import java.time.LocalDate;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.ivo.javatraing.util.Utill;

@Entity
public class Product implements Serializable {
	@Id
	int id;
	
	@NotNull(message = "Product can not be null.")
    @NotEmpty(message = "Product can not be empty.")
	String name;
	
	@NotNull(message = "Product must be relate to category.")
    @NotEmpty(message = "Product category can not be empty.")
	String category;
	
	String description;
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
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
		this.createdDate = createdDate;;
	}

	int quantity;
	

	@Column(name ="created_date")
	@JsonProperty("createdDate")
	@JsonFormat(pattern="dd-MM-yyyy")
	private LocalDate createdDate;
	

	@Column(name ="last_modified_date")
	@JsonProperty("lastModifiedDate")
	@JsonFormat(pattern="dd-MM-yyyy")
	private LocalDate lastModifiedDate;

	public LocalDate getLastModifiedDate() {
		return lastModifiedDate;
	}

	public void setLastModifiedDate(LocalDate lastModifiedDate) {
		this.lastModifiedDate = lastModifiedDate;
	}


}
