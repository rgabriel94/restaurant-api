package com.gabriel.restaurant.order.interfaces;

import com.gabriel.restaurant.order.entity.Order;

import java.util.List;
import java.util.Optional;

public interface OrderService {

    List<Order> listAllOrders();

    Optional<Order> getOrder(long orderId);

    Order createOrder();

    Order closeOrder(long order);

    void deleteOrder(long orderId);
}
