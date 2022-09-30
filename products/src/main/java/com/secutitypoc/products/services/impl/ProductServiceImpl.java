package com.secutitypoc.products.services.impl;

import com.secutitypoc.products.dto.ProductDto;
import com.secutitypoc.products.exceptions.DataNotFoundException;
import com.secutitypoc.products.models.Product;
import com.secutitypoc.products.repositories.ProductsRepository;
import com.secutitypoc.products.services.ProductsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductsService {
    @Autowired
    private ProductsRepository productsRepository;

    @Override
    public Product getProductById(Long id) throws DataNotFoundException {
        Optional<Product> productOptional = productsRepository.findById(id);
        if(productOptional.isEmpty()) throw new DataNotFoundException("Data not found");
        return productOptional.get();
    }

    @Override
    public Page<Product> getAllProducts(Pageable pageable) {
        return productsRepository.findAll(pageable);
    }

    @Override
    public Product addProduct(ProductDto product) {
        Product productToSave = Product
                .builder()
                .name(product.getName())
                .description(product.getDescription())
                .price(product.getPrice())
                .creationDate(product.getCreationDate())
                .build();
        return productsRepository.save(productToSave);
    }
}
