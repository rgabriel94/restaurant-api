package com.gabriel.restaurant.order.exception;

import org.springframework.http.HttpStatus;

public class EntityActionException extends ResponseException {

    public static final String ORDER_NOT_SAVE = "Order not saved.";
    public static final String ORDER_LINE_NOT_SAVE = "Order line not saved.";

    public static final String ORDER_ID_NULL = "Order id is null.";
    public static final String ORDER_LINE_ID_NULL = "Order line id is null.";

    public static final String ORDER_LINE_NULL = "You can't update de order price because the order line is null";

    public static final String ORDER_CLOSED = "You can't update or delete the order. It's closed. Order id -> %d";
    public static final String ORDER_EMPTY = "You can't closed the order. It haven't order lines. Order id -> %d";

    public EntityActionException(String response) {
        super(HttpStatus.BAD_REQUEST, response);
    }

    public EntityActionException(String response, Throwable throwable) {
        super(HttpStatus.BAD_REQUEST, response, throwable);
    }
}
