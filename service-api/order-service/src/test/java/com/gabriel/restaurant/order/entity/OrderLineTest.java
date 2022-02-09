package com.gabriel.restaurant.order.entity;

import com.gabriel.restaurant.order.util.EntityBuilderForTest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class OrderLineTest implements EntityBuilderForTest {
    OrderLine orderLine;

    @BeforeEach
    void beforeEach() {
        Order order = orderBuilder();
        orderLine = Mockito.spy(orderLineBuilder(order));
    }

    @AfterEach
    void afterEach() {
        orderLine = null;
    }

    @Test
    void totalPriceOfLineTest() {
        Assertions.assertEquals(2.2D, orderLine.totalPriceOfLine());
    }
}
