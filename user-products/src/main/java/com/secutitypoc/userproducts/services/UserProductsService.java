package com.secutitypoc.userproducts.services;

import com.secutitypoc.userproducts.exceptions.InvalidRequestException;
import com.secutitypoc.userproducts.exceptions.UserNotFoundException;
import com.secutitypoc.userproducts.http.feign.models.Product;
import com.secutitypoc.userproducts.models.rest.Request;
import com.secutitypoc.userproducts.models.rest.Response;

import java.util.List;

public interface UserProductsService {
    List<Product> getAllUserProductsDetailsByUserId(Long userId) throws UserNotFoundException;
    Response addProductsToUser(Request request) throws InvalidRequestException, UserNotFoundException;
    Response removeProductsFromUser(Request request) throws UserNotFoundException, InvalidRequestException;
}
