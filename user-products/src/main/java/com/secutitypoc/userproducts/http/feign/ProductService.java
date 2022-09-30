package com.secutitypoc.userproducts.http.feign;

import com.secutitypoc.userproducts.http.feign.models.Product;
import org.springframework.http.ResponseEntity;

public interface ProductService {
    ResponseEntity<Product> getProductById(Long id);
    Boolean existsProductId(Long id);
}
