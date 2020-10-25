package com.ak.retail.controller;



import com.ak.retail.Exception.InvalidPayloadException;
import com.ak.retail.model.Product;
import com.ak.retail.service.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping(path = "/products/")
public class ProductsController {
	private static final Logger logger = LoggerFactory.getLogger(ProductsController.class);
	private final ProductService productService;
	
	@Autowired
	ProductsController(ProductService productService) {
        this.productService = productService;
    }
	
	@GetMapping( path = "/{id}")
	public ResponseEntity getProductDetails(@PathVariable("id") Long id) {
		logger.info("Message Reached:" + id);
		Product product = productService.getProductDetails(id);
		return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(product);
	}
	
	@GetMapping( path = "/v3/{id}")
	public ResponseEntity getProduct(@PathVariable("id") Long id) {
		Product product = productService.getProduct(id);
		return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(product);
	}

	@PutMapping( path = "/{id}")
	public ResponseEntity updateProductDetails(@RequestBody Product payload) {
		logger.info("Message Reached:" + payload.toString());
		if(payload == null || payload.getCurrent_price() == null) {
			String err = "Payload cannot be null";
			logger.error(err);
			throw new InvalidPayloadException(err);
		}
		Product product = productService.updateProductDetails(payload);
		return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(product);

	}
}
