package com.gabriel.restaurant.order.service;

import com.gabriel.restaurant.order.entity.Order;
import com.gabriel.restaurant.order.interfaces.OrderService;
import com.gabriel.restaurant.order.interfaces.OrderStatusService;
import com.gabriel.restaurant.order.repository.OrderRepository;
import com.gabriel.restaurant.order.exception.EntityActionException;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Log4j2
@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    OrderStatusService orderStatusService;

    @Autowired
    OrderRepository orderRepository;

    @Override
    public List<Order> listAllOrders() {
        return orderRepository.findAll();
    }

    @Override
    public Optional<Order> getOrder(long orderId) {
        return orderRepository.findById(orderId);
    }

    @Override
    public Order createOrder() {
        Order order = buildOrder();
        return saveAndFlush(order);
    }

    @Override
    public Order closeOrder(long orderId) {
        Order order = orderStatusService.validateAndChangeOrderToClose(getOrder(orderId));
        return saveAndFlush(order);
    }

    @Override
    public void deleteOrder(long orderId) {
        orderStatusService.validateOrderNotClosed(getOrder(orderId));
        orderRepository.deleteById(orderId);
    }

    Order saveAndFlush(Order order) {
        try {
            return orderRepository.saveAndFlush(order);
        } catch (Exception e) {
            logErrorWrite(e);
            throw new EntityActionException(EntityActionException.ORDER_NOT_SAVE);
        }
    }

    Order buildOrder() {
        return new Order();
    }

    void logErrorWrite(Exception e) {
        log.error(e);
    }
}
