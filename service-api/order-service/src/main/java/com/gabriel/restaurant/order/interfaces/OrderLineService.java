package com.gabriel.restaurant.order.interfaces;

import com.gabriel.restaurant.order.entity.OrderLine;

import java.util.Optional;

public interface OrderLineService {

    Optional<OrderLine> getOrderLine(long orderLineId);

    OrderLine createOrderLine(OrderLine orderLine);

    OrderLine updateProductQuantity(long orderLineId, int quantity);

    void deleteOrderLine(long orderLineId);
}
