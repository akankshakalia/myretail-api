package com.ak.retail.model;

import java.io.Serializable;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Document(collection="Product")
@Getter
@Setter
@ToString
public class Product implements Serializable {
	private static final long serialVersionUID = 8039678720673462168L;
	@Id
	@JsonIgnore
	private ObjectId id;
	@JsonProperty("id")	
	private Long productId;
	private String name;
	private ProductPrice current_price;
}
