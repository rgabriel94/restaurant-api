package com.gabriel.restaurant.product.exception;

import org.springframework.http.HttpStatus;

public class EntityActionException extends ResponseException {

    public static final String PRODUCT_NOT_SAVE = "Product not saved.";
    public static final String CATEGORY_NOT_SAVE = "Category not saved.";

    public static final String PRODUCT_ID_NULL = "Product id is null.";
    public static final String CATEGORY_ID_NULL = "Category id is null.";

    public EntityActionException(String response) {
        super(HttpStatus.BAD_REQUEST, response);
    }

    public EntityActionException(String response, Throwable throwable) {
        super(HttpStatus.BAD_REQUEST, response, throwable);
    }
}
