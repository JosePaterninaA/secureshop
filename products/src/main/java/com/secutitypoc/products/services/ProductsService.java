package com.secutitypoc.products.services;

import com.secutitypoc.products.dto.ProductDto;
import com.secutitypoc.products.exceptions.DataNotFoundException;
import com.secutitypoc.products.models.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProductsService {
    Product getProductById(Long id) throws DataNotFoundException;

    Page<Product> getAllProducts(Pageable pageable);

    Product addProduct(ProductDto product);

    Boolean existsProductById(Long id);
}
