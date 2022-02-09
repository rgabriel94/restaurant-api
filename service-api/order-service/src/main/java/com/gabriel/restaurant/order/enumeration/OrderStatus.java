package com.gabriel.restaurant.order.enumeration;

public enum OrderStatus {
    PENDING,
    CLOSED;

    public boolean isPending() {
        return this == OrderStatus.PENDING;
    }

    public boolean isClosed() {
        return this == OrderStatus.CLOSED;
    }
}
