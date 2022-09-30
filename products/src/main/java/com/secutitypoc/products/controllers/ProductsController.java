package com.secutitypoc.products.controllers;

import com.secutitypoc.products.dto.ProductDto;
import com.secutitypoc.products.exceptions.DataNotFoundException;
import com.secutitypoc.products.models.Product;
import com.secutitypoc.products.services.ProductsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class ProductsController {
    @Autowired
    private ProductsService productsService;

    @GetMapping("/products/{id}")
    public ResponseEntity<Product> productById(@PathVariable("id") Long id) throws DataNotFoundException {
        return ResponseEntity.ok(productsService.getProductById(id));
    }

    @GetMapping("/products")
    public ResponseEntity<Page<Product>> allProducts(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "4") int size){
        Pageable pageable = PageRequest.of(page,size);
        return ResponseEntity.ok(productsService.getAllProducts(pageable));

    }

    @PostMapping("/products")
    public ResponseEntity<Product> addProduct(@RequestBody ProductDto product){
        return ResponseEntity.ok(productsService.addProduct(product));
    }

    @GetMapping("/products/validate/{id}")
    public ResponseEntity<Boolean> existsProductById(@PathVariable("id") Long id){
        return ResponseEntity.ok(productsService.existsProductById(id));
    }
}
