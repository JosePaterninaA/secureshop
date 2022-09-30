package com.secutitypoc.userproducts.exceptions;

public class InvalidRequestException extends Throwable {
    public InvalidRequestException(String invalid_request) {
        super(invalid_request);
    }
}
