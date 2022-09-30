package com.secutitypoc.userproducts;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class UserProductsApplication {

	public static void main(String[] args) {
		SpringApplication.run(UserProductsApplication.class, args);
	}

}
