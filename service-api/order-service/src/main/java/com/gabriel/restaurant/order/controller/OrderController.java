package com.gabriel.restaurant.order.controller;

import com.gabriel.restaurant.order.entity.Order;
import com.gabriel.restaurant.order.interfaces.OrderService;
import com.gabriel.restaurant.order.model.dto.order.OrderResponseDTO;
import com.gabriel.restaurant.order.exception.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "api/orders")
public class OrderController extends BaseController {

    @Autowired
    OrderService orderService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<OrderResponseDTO> listAllOrders() {
        List<Order> orders = orderService.listAllOrders();
        return mapper.convert(orders, OrderResponseDTO.class);
    }

    @GetMapping(value = "/{orderId}")
    @ResponseStatus(HttpStatus.OK)
    public OrderResponseDTO getOrder(@PathVariable Long orderId) {
        Order order = orderService.getOrder(orderId).orElseThrow(
                () -> new NotFoundException(NotFoundException.ORDER_NOT_FOUND));
        return mapper.convert(order, OrderResponseDTO.class);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public OrderResponseDTO createOrder() {
        Order order = orderService.createOrder();
        return mapper.convert(order, OrderResponseDTO.class);
    }

    @PutMapping(value = "/closed/{orderId}")
    @ResponseStatus(HttpStatus.OK)
    public OrderResponseDTO closeOrder(@PathVariable Long orderId) {
        Order order = orderService.closeOrder(orderId);
        return mapper.convert(order, OrderResponseDTO.class);
    }

    @DeleteMapping(value = "/{orderId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteOrder(@PathVariable Long orderId) {
        orderService.deleteOrder(orderId);
    }
}
