package com.ak.retail.controller;



import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ak.retail.model.Product;
import com.ak.retail.service.ProductService;


@RestController
@RequestMapping(path = "/products/")
public class ProductsController {
	private static final Logger logger = LoggerFactory.getLogger(ProductsController.class);
	private ProductService productService;
	
	@Autowired
	ProductsController(ProductService productService) {
        this.productService = productService;
    }
	
	@GetMapping( path = "/{id}")
	public ResponseEntity getProductDetails(@PathVariable("id") Long id) {
		try {
			logger.info("Message Reached:" + id);
			Product product = productService.getProductDetails(id);
			return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(product);
		} catch (Exception ex) {
			logger.error(ex.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).contentType(MediaType.APPLICATION_JSON)
					.body("ERROR: " + ex.getMessage());
		}
	}
	
	@GetMapping( path = "/v3/{id}")
	public ResponseEntity getProduct(@PathVariable("id") Long id) {
		try {
			Product product = productService.getProduct(id);
			return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(product);
		} catch (Exception ex) {
			logger.error(ex.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).contentType(MediaType.APPLICATION_JSON)
					.body("ERROR: " + ex.getMessage());
		}
	}
	
	@PutMapping( path = "/{id}")
	public ResponseEntity updateProductDetails(@RequestBody Product payload) {
		try {
			logger.info("Message Reached:" + payload.toString());
			if(payload == null || payload.getCurrent_price() == null) {
				String err = "ERROR: payload cannot be null";
				logger.error(err);
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).contentType(MediaType.APPLICATION_JSON)
						.body(err);
			}
			Product product = productService.updateProductDetails(payload);
			return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(product);
		} catch (Exception ex) {
			logger.error(ex.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).contentType(MediaType.APPLICATION_JSON)
					.body("ERROR: " + ex.getMessage());
		}
	}
}
