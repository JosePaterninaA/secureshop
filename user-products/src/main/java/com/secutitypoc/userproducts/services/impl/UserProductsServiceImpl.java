package com.secutitypoc.userproducts.services.impl;

import com.secutitypoc.userproducts.exceptions.InvalidRequestException;
import com.secutitypoc.userproducts.exceptions.UserNotFoundException;
import com.secutitypoc.userproducts.http.feign.ProductService;
import com.secutitypoc.userproducts.http.feign.models.Product;
import com.secutitypoc.userproducts.models.UserProducts;
import com.secutitypoc.userproducts.models.rest.Request;
import com.secutitypoc.userproducts.models.rest.Response;
import com.secutitypoc.userproducts.models.rest.impl.AddUserProductsResponse;
import com.secutitypoc.userproducts.models.rest.impl.RemoveUserProductsResponse;
import com.secutitypoc.userproducts.repositories.UserProductsRepository;
import com.secutitypoc.userproducts.services.UserProductsService;
import com.secutitypoc.userproducts.utils.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserProductsServiceImpl implements UserProductsService {

    @Autowired
    private UserProductsRepository userProductsRepository;

    @Autowired
    private ProductService productService;

    @Override
    public List<Product> getAllUserProductsDetailsByUserId(Long userId) throws UserNotFoundException {
        Optional<UserProducts> userProducts = userProductsRepository.findByUserId(userId);
        if(userProducts.isEmpty()) throw new UserNotFoundException(Constants.DATA_NOT_FOUND_MESSAGE);
        List<Long> productIds = userProducts.get().getProductIds();
        return productIds
                .stream()
                .map(productId ->{
                    ResponseEntity<Product> productResponse = productService.getProductById(productId);
                    return productResponse.getStatusCode() == HttpStatus.OK? productResponse.getBody():null;
                }).collect(Collectors.toList());
    }

    @Override
    public Response addProductsToUser(Request request) throws InvalidRequestException, UserNotFoundException {
        if(request.getUserId() == null || request.getProductIds() == null) throw new InvalidRequestException(Constants.INVALID_REQUEST_MESSAGE);
        Optional<UserProducts> userProductsOptional = userProductsRepository.findByUserId(request.getUserId());
        if(userProductsOptional.isEmpty()) throw new UserNotFoundException(Constants.DATA_NOT_FOUND_MESSAGE);
        UserProducts userProducts= userProductsOptional.get();
        List<String> responseCodes = request
                .getProductIds()
                .stream()
                .map(productId -> {
                    if(!productService.existsProductId(productId) || userProducts.getProductIds().contains(productId)) return Constants.FAILED;
                    userProducts.getProductIds().add(productId);
                    return Constants.ADDED;
                })
                .collect(Collectors.toList());
        userProductsRepository.save(userProducts);
        Response response = new AddUserProductsResponse();
        response.setUserId(userProducts.getUserId());
        response.setResponses(responseCodes);
        return response;
    }

    @Override
    public Response removeProductsFromUser(Request request) throws UserNotFoundException, InvalidRequestException {
        if(request.getUserId() == null || request.getProductIds() == null) throw new InvalidRequestException(Constants.INVALID_REQUEST_MESSAGE);
        Optional<UserProducts> userProductsOptional = userProductsRepository.findByUserId(request.getUserId());
        if(userProductsOptional.isEmpty()) throw new UserNotFoundException(Constants.DATA_NOT_FOUND_MESSAGE);
        UserProducts userProducts= userProductsOptional.get();
        List<String> responseCodes = request
                .getProductIds()
                .stream()
                .map(productId -> {
                    if(!productService.existsProductId(productId)) return Constants.FAILED;
                    userProducts.getProductIds().remove(productId);
                    return Constants.REMOVED;
                })
                .collect(Collectors.toList());
        userProductsRepository.save(userProducts);
        Response response = new RemoveUserProductsResponse();
        response.setUserId(userProducts.getUserId());
        response.setResponses(responseCodes);
        return response;
    }
}
