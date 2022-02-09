package com.gabriel.restaurant.order.interfaces;

import com.gabriel.restaurant.order.entity.Order;

import java.util.Optional;

public interface OrderStatusService {

    Order validateAndChangeOrderToClose(Optional<Order> order);

    void validateOrderNotClosed(Optional<Order> optionalOrder);
}
