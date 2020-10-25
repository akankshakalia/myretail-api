package com.ak.retail.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;

import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

@Configuration
public class AppConfig {
	@Value("${spring.data.mongodb.uri}")
    private String connectionString;

	public @Bean MongoClient mongoClient() {
	       return MongoClients.create(connectionString);
   }
	
   @Bean
   public RestTemplate getRestTemplate() {
      return new RestTemplate();
   }
   
   @Bean
   public Docket productApi() {
      return new Docket(DocumentationType.SWAGGER_2).select()
         .apis(RequestHandlerSelectors.basePackage("com.ak.retail")).build();
   }
}
