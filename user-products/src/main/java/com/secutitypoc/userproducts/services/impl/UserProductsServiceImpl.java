package com.secutitypoc.userproducts.services.impl;

import com.secutitypoc.userproducts.exceptions.InvalidRequestException;
import com.secutitypoc.userproducts.exceptions.UserNotFoundException;
import com.secutitypoc.userproducts.http.feign.ProductsService;
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

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserProductsServiceImpl implements UserProductsService {

    @Autowired
    private UserProductsRepository userProductsRepository;

    @Autowired
    private ProductsService productsService;

    @Override
    public List<Product> getAllUserProductsDetailsByUserId(Long userId) throws UserNotFoundException {
        Optional<UserProducts> userProducts = userProductsRepository.findByUserId(userId);
        if(userProducts.isEmpty()) throw new UserNotFoundException(Constants.DATA_NOT_FOUND_MESSAGE);
        Set<Long> productIds = userProducts.get().getProductIds();
        return productIds
                .stream()
                .map(productId ->{
                    ResponseEntity<Product> productResponse = productsService.getProductById(productId);
                    return productResponse.getStatusCode() == HttpStatus.OK? productResponse.getBody():null;
                }).collect(Collectors.toList());
    }

    @Override
    public Response addProductsToUser(Request request) throws InvalidRequestException, UserNotFoundException {
        if(request.getUserId() == null || request.getProductIds() == null) throw new InvalidRequestException(Constants.INVALID_REQUEST_MESSAGE);
        Optional<UserProducts> userProductsOptional = userProductsRepository.findByUserId(request.getUserId());
        Response response = new AddUserProductsResponse();

        if(userProductsOptional.isPresent()){
            UserProducts userProducts= userProductsOptional.get();
            List<String> responseCodes = request
                    .getProductIds()
                    .stream()
                    .map(productId -> {
                        if (!productsService.existsProductId(productId) || userProducts.getProductIds().contains(productId))
                            return Constants.FAILED;
                        userProducts.getProductIds().add(productId);
                        return Constants.ADDED;
                    }).toList();
            userProductsRepository.save(userProducts);
            response.setUserId(userProducts.getUserId());
            response.setResponses(responseCodes);

        }else {
            UserProducts userProducts = new UserProducts();
            userProducts.setUserId(request.getUserId());
            userProducts.setProductIds(new HashSet<>());
            List<String> responseCodes = request
                    .getProductIds()
                    .stream()
                    .map(productId -> {
                        if (!productsService.existsProductId(productId)) return Constants.FAILED;
                        userProducts.getProductIds().add(productId);
                        return Constants.ADDED;
                    }).toList();
            userProductsRepository.save(userProducts);
            response.setUserId(userProducts.getUserId());
            response.setResponses(responseCodes);
        }
        return response;
    }

    @Override
    public Response removeProductsFromUser(Request request) throws UserNotFoundException, InvalidRequestException {
        if(request.getUserId() == null || request.getProductIds() == null) throw new InvalidRequestException(Constants.INVALID_REQUEST_MESSAGE);
        Optional<UserProducts> userProductsOptional = userProductsRepository.findByUserId(request.getUserId());
        Response response = new RemoveUserProductsResponse();

        if(userProductsOptional.isEmpty()){
            UserProducts userProducts= new UserProducts();
            userProducts.setUserId(request.getUserId());
            userProducts.setProductIds(new HashSet<>());
            List<String> responseCodes = request
                    .getProductIds()
                    .stream()
                    .map(productId -> Constants.FAILED)
                    .toList();
            response.setUserId(userProducts.getUserId());
            response.setResponses(responseCodes);
        }else{
            UserProducts userProducts= userProductsOptional.get();
            List<String> responseCodes = request
                    .getProductIds()
                    .stream()
                    .map(productId -> {
                        if(!productsService.existsProductId(productId)) return Constants.FAILED;
                        userProducts.getProductIds().remove(productId);
                        return Constants.REMOVED;
                    })
                    .toList();
            userProductsRepository.save(userProducts);
            response.setUserId(userProducts.getUserId());
            response.setResponses(responseCodes);
        }
        return response;
    }
}
