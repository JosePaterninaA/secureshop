package com.secutitypoc.userproducts.models.rest;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public abstract class Request {
    private Long userId;
    private List<Long> productIds;
}
