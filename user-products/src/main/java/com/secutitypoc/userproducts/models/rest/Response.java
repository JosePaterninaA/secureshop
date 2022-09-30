package com.secutitypoc.userproducts.models.rest;

import lombok.Data;

import java.util.List;

@Data
public abstract class Response {
    private Long userId;
    private List<String> responses;
}
