package com.ak.retail.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.ak.retail.model.Product;

public interface ProductsRepository extends MongoRepository<Product, Long> {
	Optional<Product> findByProductId(Long productId);
}

