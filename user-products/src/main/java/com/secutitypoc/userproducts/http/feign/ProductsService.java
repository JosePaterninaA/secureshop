package com.secutitypoc.userproducts.http.feign;

import com.secutitypoc.userproducts.http.feign.models.Product;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "products", url = "http://localhost:8081")
public interface ProductsService {
    @GetMapping("/products/{id}")
    ResponseEntity<Product> getProductById(@PathVariable("id") Long id);
    @GetMapping("/products/validate/{id}")
    Boolean existsProductId(@PathVariable("id") Long id);
}
