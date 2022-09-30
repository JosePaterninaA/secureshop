package com.secutitypoc.userproducts.controllers;

import com.secutitypoc.userproducts.exceptions.InvalidRequestException;
import com.secutitypoc.userproducts.exceptions.UserNotFoundException;
import com.secutitypoc.userproducts.http.feign.models.Product;
import com.secutitypoc.userproducts.models.rest.Request;
import com.secutitypoc.userproducts.models.rest.impl.AddUserProductsRequest;
import com.secutitypoc.userproducts.models.rest.impl.AddUserProductsResponse;
import com.secutitypoc.userproducts.models.rest.impl.RemoveUserProductsResponse;
import com.secutitypoc.userproducts.models.rest.impl.RemoveUserProductsRequest;
import com.secutitypoc.userproducts.services.UserProductsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserProductsController {

    @Autowired
    private UserProductsService userProductsService;

    @GetMapping("/user-products/{id}")
    public ResponseEntity<List<Product>> allUserProductsDetails(@PathVariable("id") Long userId) throws UserNotFoundException {
        return ResponseEntity.ok(userProductsService.getAllUserProductsDetailsByUserId(userId));
    }

    @PostMapping("/user-products/add")
    public ResponseEntity<AddUserProductsResponse> addProductsToUser(@RequestBody AddUserProductsRequest request) throws UserNotFoundException, InvalidRequestException {
        return ResponseEntity.ok((AddUserProductsResponse) userProductsService.addProductsToUser(request));
    }

    @PostMapping("/user-products/remove")
    public ResponseEntity<RemoveUserProductsResponse> removeProductsFromUser(@RequestBody RemoveUserProductsRequest request) throws UserNotFoundException, InvalidRequestException {
        return ResponseEntity.ok((RemoveUserProductsResponse) userProductsService.removeProductsFromUser(request));
    }
}
