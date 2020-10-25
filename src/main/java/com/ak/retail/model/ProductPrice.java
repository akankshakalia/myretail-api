package com.ak.retail.model;

import java.io.Serializable;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Document(collection="ProductPrice")
@Getter
@Setter
@ToString
public class ProductPrice implements Serializable {
	private static final long serialVersionUID = 8039678720673462168L;
	@Id
	@JsonIgnore
	private ObjectId id;
	@JsonIgnore
	private Long productId;
	private String currency_code;
	private Double value;
}
