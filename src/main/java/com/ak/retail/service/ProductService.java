package com.ak.retail.service;

import org.springframework.web.client.HttpStatusCodeException;

import com.ak.retail.model.Product;

public interface ProductService {
	Product getProductDetails(Long id) throws HttpStatusCodeException;
	Product getProduct(Long id);
	Product updateProductDetails(Product product);
}
