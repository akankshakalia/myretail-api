package com.ak.retail.service.impl;

import java.util.Optional;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import com.ak.retail.model.Product;
import com.ak.retail.model.ProductPrice;
import com.ak.retail.repository.PriceRepository;
import com.ak.retail.repository.ProductsRepository;
import com.ak.retail.service.ProductService;

@SpringBootTest
@RunWith(SpringRunner.class)
public class ProductServiceImplTest {
	
	@Value("${api.uri}")
    private String uri;
	
	@MockBean
	private ProductsRepository productRepository;
	
	@MockBean
	private PriceRepository priceRepository;
	
	@Autowired
    private ProductService productService;
	
	@MockBean
    private RestTemplate restTemplate;
	
	Long productId = 13860428L;

	
	@Before
	public void setUp() {}
	
	@Test
	public void getProductDetails() throws Exception {
		Optional<ProductPrice> optonalPrice = Optional.ofNullable(getProductPriceMockData());
	    Mockito
	    .when(priceRepository.findByProductId(productId))
	    .thenReturn(optonalPrice);
	    
	    Mockito
        .when(restTemplate.getForEntity(uri+productId, Product.class))
        .thenReturn(new ResponseEntity<Product>(getProductMockData(), HttpStatus.OK));
	    
		Product result = productService.getProductDetails(productId);
		
		Assert.assertEquals(result.getProductId(), productId);
		Assert.assertNotNull(result);
		Assert.assertEquals(result.getCurrent_price().getCurrency_code(), getProductPriceMockData().getCurrency_code());
		Assert.assertEquals(result.getCurrent_price().getValue(), getProductPriceMockData().getValue());
	}
	
	@Test
	public void getProduct() throws Exception {
		Optional<Product> optonalProduct = Optional.ofNullable(getProductMockData());
	    Mockito
	    .when(productRepository.findByProductId(productId))
	    .thenReturn(optonalProduct);
	    
		Product result = productService.getProduct(productId);
		
		Assert.assertEquals(result.getProductId(), productId);
		Assert.assertEquals(result.getName(), getProductMockData().getName());
	}
	
	@Test
	public void updateProductDetails() throws Exception {
		Product product = getProductMockData();
		ProductPrice price = getProductPriceMockData();
		price.setValue(20.55);
		price.setCurrency_code("INR");
		product.setCurrent_price(price);
		Optional<ProductPrice> optonalPrice = Optional.ofNullable(getProductPriceMockData());
	    Mockito
	    .when(priceRepository.findByProductId(productId))
	    .thenReturn(optonalPrice);
	    
		Product result = productService.updateProductDetails(getProductDetailsMockData());
		
		Assert.assertEquals(result.getProductId(), productId);
		Assert.assertEquals(result.getCurrent_price().getCurrency_code(), "INR");
	}
	
	
	private Product getProductMockData() {
		Product product = new Product();
		product.setProductId(productId);
		product.setName("The Big Lebowski (Blu-ray) (Widescreen)");
		
		return product;
	}
	
	private ProductPrice getProductPriceMockData() {
		ProductPrice price = new ProductPrice();
		price.setProductId(productId);
	    price.setCurrency_code("USD");
	    price.setValue(15.26);
		
		return price;
	}
	
	@SuppressWarnings("unused")
	private Product getProductDetailsMockData() {
		Product product = getProductMockData();
		ProductPrice price = getProductPriceMockData();
		product.setCurrent_price(price);
		
		return product;
	}
}


