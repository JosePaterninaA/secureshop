package com.secutitypoc.products;

import com.secutitypoc.products.dto.ProductDto;
import com.secutitypoc.products.services.ProductsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;

import java.time.LocalDate;

@SpringBootApplication
@EnableDiscoveryClient
public class ProductsApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProductsApplication.class, args);
	}

	@Autowired
	private ProductsService productsService;

//	@Bean
	public ApplicationRunner init(){
		return args -> productsService.addProduct(
				ProductDto
				.builder()
				.name("Screwdriver phillips 45")
				.description("The best Screwdriver in this side of the Mississipi")
				.price(20d)
				.creationDate(LocalDate.of(2022,9,29))
				.build());
	}

}
