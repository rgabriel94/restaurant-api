package com.gabriel.restaurant.order.util;

import com.gabriel.restaurant.order.entity.Order;
import com.gabriel.restaurant.order.entity.OrderLine;
import com.gabriel.restaurant.order.enumeration.OrderStatus;
import com.gabriel.restaurant.order.model.dto.order.OrderResponseDTO;
import com.gabriel.restaurant.order.model.dto.orderline.OrderLineCreateRequestDTO;
import com.gabriel.restaurant.order.model.dto.orderline.OrderLineResponseDTO;
import com.gabriel.restaurant.order.model.dto.orderline.OrderLineUpdateRequestDTO;
import com.gabriel.restaurant.order.model.dto.product.Product;

import java.util.ArrayList;

public interface EntityBuilderForTest {
    Long defaultId = 1L;

    String productName = "Pizza";
    String productDescription = "Description Pizza";
    double productPrice = 1.1D;
    int quantity = 2;

    default Order orderBuilder() {
        return new Order();
    }

    default OrderLine orderLineBuilder(Order order) {
        return OrderLine.builder()
                .order(order)
                .productId(defaultId)
                .productName(productName)
                .productPrice(productPrice)
                .quantity(quantity)
                .build();
    }

    default OrderLine orderLineBuilder(Order order, Long orderLineId) {
        OrderLine orderLine = orderLineBuilder(order);
        orderLine.setId(orderLineId);
        return orderLine;
    }

    default Product productBuilder() {
        return Product.builder()
                .id(defaultId)
                .productName(productName)
                .description(productDescription)
                .price(productPrice)
                .categoryId(defaultId)
                .build();
    }

    default OrderResponseDTO orderResponseBuilder() {
        return OrderResponseDTO.builder()
                .id(null)
                .closingDate(null)
                .closingPrice(0D)
                .status(OrderStatus.PENDING)
                .orderLines(new ArrayList<>())
                .build();
    }

    default OrderLineCreateRequestDTO orderLineCreateRequestBuilder() {
        return OrderLineCreateRequestDTO.builder()
                .id(defaultId)
                .orderId(defaultId)
                .productId(defaultId)
                .quantity(quantity)
                .build();
    }

    default OrderLineUpdateRequestDTO orderLineUpdateRequestBuilder() {
        return OrderLineUpdateRequestDTO.builder()
                .id(defaultId)
                .quantity(quantity)
                .build();
    }

    default OrderLineResponseDTO orderLineResponseBuilder() {
        return OrderLineResponseDTO.builder()
                .id(defaultId)
                .orderId(defaultId)
                .productId(defaultId)
                .productName(productName)
                .productPrice(productPrice)
                .quantity(quantity)
                .build();
    }
}
