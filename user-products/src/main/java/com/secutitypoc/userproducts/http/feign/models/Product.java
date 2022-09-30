package com.secutitypoc.userproducts.http.feign.models;

import lombok.*;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Product {
    private Long id;
    private String name;
    private String description;
    private Double price;
    private LocalDate creationDate;
}