package com.gabriel.restaurant.order.exception;

import org.springframework.http.HttpStatus;

public class NotFoundException extends ResponseException {

    public static final String ORDER_NOT_FOUND = "Order not found.";
    public static final String ORDER_LINE_NOT_FOUND = "Order line not found.";
    public static final String PRODUCT_NOT_FOUND = "Product not found";

    public NotFoundException(String response) {
        super(HttpStatus.NOT_FOUND, response);
    }
}
