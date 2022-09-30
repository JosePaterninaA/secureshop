package com.secutitypoc.userproducts.models.rest;

import lombok.Data;

import java.util.List;

@Data
public abstract class Request {
    private Long userId;
    private List<Long> productIds;
}
