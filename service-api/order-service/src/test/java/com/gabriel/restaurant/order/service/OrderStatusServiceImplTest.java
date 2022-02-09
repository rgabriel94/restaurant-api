package com.gabriel.restaurant.order.service;

import com.gabriel.restaurant.order.OrderServiceApplication;
import com.gabriel.restaurant.order.entity.Order;
import com.gabriel.restaurant.order.enumeration.OrderStatus;
import com.gabriel.restaurant.order.exception.EntityActionException;
import com.gabriel.restaurant.order.exception.NullException;
import com.gabriel.restaurant.order.util.EntityBuilderForTest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;

import java.util.ArrayList;
import java.util.Optional;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest(classes = {OrderServiceApplication.class})
class OrderStatusServiceImplTest implements EntityBuilderForTest {
    private static Order order;

    @SpyBean
    OrderStatusServiceImpl orderStatusService;

    @BeforeAll
    static void beforeAll() {
        order = new Order();
    }

    @BeforeEach
    void beforeEach() {
        order.getOrderLines().add(orderLineBuilder(order, 1L));
        order.getOrderLines().add(orderLineBuilder(order, 2L));
    }

    @AfterEach
    void afterEach() {
        order.setClosingDate(null);
        order.setStatus(OrderStatus.PENDING);
        order.setClosingPrice(0);
        order.setOrderLines(new ArrayList());
    }

    @Test
    void validateAndChangeOrderToCloseTest() {
        Mockito.doReturn(order).when(orderStatusService)
                .validateOrderToClose(Mockito.any());
        Order testOrder = orderStatusService.validateAndChangeOrderToClose(Optional.ofNullable(order));
        Assertions.assertNotNull(testOrder.getClosingDate());
        Assertions.assertEquals(4.4D, testOrder.getClosingPrice());
        Assertions.assertEquals(OrderStatus.CLOSED, testOrder.getStatus());
    }

    @Test
    void validateOrderNotClosedTest() {
        Mockito.doReturn(order).when(orderStatusService)
                .isOrderNull(Mockito.any());
        Mockito.doNothing().when(orderStatusService)
                .isOrderClosed(Mockito.any());
        Assertions.assertDoesNotThrow(
                () -> orderStatusService.validateOrderNotClosed(Optional.ofNullable(order)));
    }

    @Test
    void changeOrderToCloseTest() {
        orderStatusService.changeOrderToClose(order);
        Assertions.assertNotNull(order.getClosingDate());
        Assertions.assertEquals(4.4D, order.getClosingPrice());
        Assertions.assertEquals(OrderStatus.CLOSED, order.getStatus());
    }

    @Test
    void validateOrderToCloseTest() {
        Mockito.doReturn(order).when(orderStatusService)
                .isOrderNull(Mockito.any());
        Mockito.doNothing().when(orderStatusService)
                .isOrderClosed(Mockito.any());
        Mockito.doNothing().when(orderStatusService)
                .isOrderEmpty(Mockito.any());
        Assertions.assertEquals(order, orderStatusService.validateOrderToClose(Optional.ofNullable(order)));
    }

    @Test
    void isOrderEmptyTest() {
        order.setOrderLines(new ArrayList<>());
        Mockito.doNothing().when(orderStatusService)
                .logWarningWrite(Mockito.any());
        Assertions.assertThrows(EntityActionException.class,
                () -> orderStatusService.isOrderEmpty(order));
    }

    @Test
    void isOrderEmptyEntityActionExceptionTest() {
        Assertions.assertDoesNotThrow(
                () -> orderStatusService.isOrderEmpty(order));
    }

    @Test
    void isOrderClosedTest() {
        Mockito.doReturn(false).when(orderStatusService)
                .isOrderStatusClosed(Mockito.any());
        Assertions.assertDoesNotThrow(
                () -> orderStatusService.isOrderClosed(order));
    }

    @Test
    void isOrderClosedEntityActionException() {
        Mockito.doReturn(true).when(orderStatusService)
                .isOrderStatusClosed(Mockito.any());
        Mockito.doNothing().when(orderStatusService)
                .logWarningWrite(Mockito.any());
        Assertions.assertThrows(EntityActionException.class,
                () -> orderStatusService.isOrderClosed(order));
    }

    @Test
    void isOrderStatusClosedTest() {
        order.setStatus(OrderStatus.CLOSED);
        Assertions.assertTrue(orderStatusService.isOrderStatusClosed(order.getStatus()));
    }

    @Test
    void isOrderStatusClosedFalseTest() {
        Assertions.assertFalse(orderStatusService.isOrderStatusClosed(order.getStatus()));
    }

    @Test
    void isOrderNullTest() {
        Assertions.assertDoesNotThrow(
                () -> orderStatusService.isOrderNull(Optional.ofNullable(order)));
    }

    @Test
    void isOrderNullExceptionTest() {
        Mockito.doNothing().when(orderStatusService)
                .logErrorWrite(Mockito.any());
        Optional<Order> emptyOptional = Optional.ofNullable(null);
        Assertions.assertThrows(NullException.class,
                () -> orderStatusService.isOrderNull(emptyOptional));
    }
}
