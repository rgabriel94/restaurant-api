package com.gabriel.restaurant.order.exception;

import org.springframework.http.HttpStatus;

public class NullException extends ResponseException {

    public static final String ORDER_NULL = "The order is null.";
    public static final String ORDER_LINE_NULL = "The order line is null.";

    public NullException(String response) {
        super(HttpStatus.BAD_REQUEST, response);
    }

    public NullException(String response, Throwable throwable) {
        super(HttpStatus.BAD_REQUEST, response, throwable);
    }
}
