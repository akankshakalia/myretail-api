package com.ak.retail.servce.impl;

import com.ak.retail.Exception.ResourceNotFoundException;
import com.ak.retail.model.Product;
import com.ak.retail.model.ProductPrice;
import com.ak.retail.repository.PriceRepository;
import com.ak.retail.repository.ProductsRepository;
import com.ak.retail.service.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService{
	private static final Logger logger = LoggerFactory.getLogger(ProductServiceImpl.class);
	
	@Value("${api.uri}")
    private String uri;
	
	@Autowired
	RestTemplate restTemplate;
	
	@Autowired
	private PriceRepository priceRepository;
	
	@Autowired
	private ProductsRepository productRepository;
	 
    @Override
	@Retryable(value = { RestClientException.class }, maxAttemptsExpression = "${retry.maxAttempts}", backoff = @Backoff(delayExpression = "${retry.delay}"))
	public Product getProductDetails(Long id) throws RestClientException, ResourceNotFoundException{
		Product product = new Product();
		product.setProductId(id);
		Optional<ProductPrice> productPrice = priceRepository.findByProductId(id);
		if(!productPrice.isPresent()){
			String err = String.format("Product with id %s does not exist ", id);
			logger.info(err);
			throw new ResourceNotFoundException(err);
		}
		product.setCurrent_price(priceRepository.findByProductId(id).get());
		try {
			product.setName(getProductName(id));
		}catch(HttpStatusCodeException ex) {
			int statusCode = ex.getStatusCode().value();
			String errorPayload = ex.getResponseBodyAsString();
			logger.error("Error calling external api");
			throw new RestClientException(errorPayload + ":"+ statusCode);
		}catch(RestClientException e){
			logger.error("Error calling external api");

			throw new RestClientException("External Service Error");
		}

		return product;
	}

	private String getProductName(Long id) throws RestClientException{
		logger.info("Calling external api");
		ResponseEntity<Product> resp = restTemplate.getForEntity(uri+id, Product.class);
		return resp.getStatusCode() == HttpStatus.OK ? resp.getBody().getName() : null;
    }
    
    @Override
	public Product getProduct(Long id) {
		return productRepository.findByProductId(id).get();
	}

	@Override
	public Product updateProductDetails(Product product) {
		ProductPrice price =
				priceRepository.findByProductId(product.getProductId()).orElse(new ProductPrice());
		if(product.getCurrent_price() !=null) {
			price.setProductId(product.getProductId());
			price.setValue(product.getCurrent_price().getValue());
			price.setCurrency_code(product.getCurrent_price().getCurrency_code());
			ProductPrice updatePrice = priceRepository.save(price);
			product.setCurrent_price(updatePrice);
		}
		return product;
	}

}
