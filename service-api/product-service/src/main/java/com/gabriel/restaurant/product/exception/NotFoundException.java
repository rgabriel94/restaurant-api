package com.gabriel.restaurant.product.exception;

import org.springframework.http.HttpStatus;

public class NotFoundException extends ResponseException {

    public static final String PRODUCT_NOT_FOUND = "Product not found.";
    public static final String CATEGORY_NOT_FOUND = "Category not found.";

    public NotFoundException(String response) {
        super(HttpStatus.NOT_FOUND, response);
    }
}
