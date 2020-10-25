package com.ak.retail.controller;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.ak.retail.AbstractTest;
import com.ak.retail.model.Product;
import com.ak.retail.model.ProductPrice;
import com.ak.retail.service.ProductService;


public class ProductControllerTest extends AbstractTest{
	

	@MockBean
	private ProductService service;
	
	Long productId = 13860428L;
	
	   @Override
	   @Before
	   public void setUp() {
	      super.setUp();
	   }
	   

	@Test
	public void getProductDetails() throws Exception {
		Mockito
	    .when(service.getProductDetails(productId))
	    .thenReturn(getProductDetailsMockData());
		
		String uri = "/products/"+productId;
		   MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(uri)
		      .accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();
		   
		   int status = mvcResult.getResponse().getStatus();
		   Assert.assertEquals(200, status);
		   String content = mvcResult.getResponse().getContentAsString();
		   Product product = mapFromJson(content, Product.class);
		   Assert.assertNotNull(product);
	}
	
	@Test
	public void getProduct() throws Exception {
		Mockito
	    .when(service.getProduct(productId))
	    .thenReturn(getProductMockData());
		
		String uri = "/products/v3/"+productId;
		   MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(uri)
		      .accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();
		   
		   int status = mvcResult.getResponse().getStatus();
		   Assert.assertEquals(200, status);
		   String content = mvcResult.getResponse().getContentAsString();
		   Product product = mapFromJson(content, Product.class);
		   Assert.assertNotNull(product);
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
