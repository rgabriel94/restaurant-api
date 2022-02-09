package com.gabriel.restaurant.order.entity;

import com.gabriel.restaurant.order.util.EntityBuilderForTest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class OrderTest implements EntityBuilderForTest {
    Order order;

    @BeforeEach
    void beforeEach() {
        order = Mockito.spy(orderBuilder());
        order.getOrderLines().add(orderLineBuilder(order, 1L));
        order.getOrderLines().add(orderLineBuilder(order, 2L));
    }

    @AfterEach
    void afterEach() {
        order = null;
    }

    @Test
    void calculateClosingPriceTest() {
        order.calculateClosingPrice();
        Assertions.assertEquals(4.4D, order.getClosingPrice());
    }
}
