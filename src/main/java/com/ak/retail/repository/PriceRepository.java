package com.ak.retail.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.ak.retail.model.ProductPrice;

public interface PriceRepository extends MongoRepository<ProductPrice, String> {
	Optional<ProductPrice> findByProductId(Long productId);

}

