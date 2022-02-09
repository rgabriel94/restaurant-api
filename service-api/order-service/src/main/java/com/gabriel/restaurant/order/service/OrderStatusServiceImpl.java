package com.gabriel.restaurant.order.service;

import com.gabriel.restaurant.order.entity.Order;
import com.gabriel.restaurant.order.enumeration.OrderStatus;
import com.gabriel.restaurant.order.interfaces.OrderStatusService;
import com.gabriel.restaurant.order.exception.EntityActionException;
import com.gabriel.restaurant.order.exception.NullException;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Log4j2
@Service
public class OrderStatusServiceImpl implements OrderStatusService {

    @Override
    public Order validateAndChangeOrderToClose(Optional<Order> optionalOrder) {
        Order order = validateOrderToClose(optionalOrder);
        changeOrderToClose(order);
        return order;
    }

    @Override
    public void validateOrderNotClosed(Optional<Order> optionalOrder) {
        Order order = isOrderNull(optionalOrder);
        isOrderClosed(order);
    }

    void changeOrderToClose(Order order) {
        order.setClosingDate(new Date());
        order.calculateClosingPrice();
        order.setStatus(OrderStatus.CLOSED);
    }

    public Order validateOrderToClose(Optional<Order> optionalOrder) {
        Order order = isOrderNull(optionalOrder);
        isOrderClosed(order);
        isOrderEmpty(order);
        return order;
    }

    void isOrderEmpty(Order order) {
        if (order.getOrderLines().isEmpty()) {
            String message = String.format(EntityActionException.ORDER_EMPTY, order.getId());
            logWarningWrite(message);
            throw new EntityActionException(message);
        }
    }

    void isOrderClosed(Order order) {
        if (isOrderStatusClosed(order.getStatus())) {
            String message = String.format(EntityActionException.ORDER_CLOSED, order.getId());
            logWarningWrite(message);
            throw new EntityActionException(message);
        }
    }

    boolean isOrderStatusClosed(OrderStatus status) {
        return status.isClosed();
    }

    Order isOrderNull(Optional<Order> order) {
        return order.orElseThrow(() -> {
            logErrorWrite(NullException.ORDER_NULL);
            return new NullException(NullException.ORDER_NULL);
        });
    }

    void logWarningWrite(String message) {
        log.warn(message);
    }

    void logErrorWrite(String message) {
        log.error(message);
    }
}
