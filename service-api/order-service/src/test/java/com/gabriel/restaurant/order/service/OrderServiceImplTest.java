package com.gabriel.restaurant.order.service;

import com.gabriel.restaurant.order.OrderServiceApplication;
import com.gabriel.restaurant.order.entity.Order;
import com.gabriel.restaurant.order.enumeration.OrderStatus;
import com.gabriel.restaurant.order.exception.EntityActionException;
import com.gabriel.restaurant.order.interfaces.OrderStatusService;
import com.gabriel.restaurant.order.util.EntityBuilderForTest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;

import java.util.Date;
import java.util.Optional;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest(classes = {OrderServiceApplication.class})
class OrderServiceImplTest implements EntityBuilderForTest {
    @SpyBean
    private OrderServiceImpl orderService;

    @MockBean
    private OrderStatusService orderStatusService;

    private Order order;
    private Long defaultOrderId;

    @BeforeEach
    void beforeEach() {
        order = orderBuilder();
        order.getOrderLines().add(orderLineBuilder(order));
        order.getOrderLines().add(orderLineBuilder(order));
        orderService.saveAndFlush(order);
        defaultOrderId = order.getId();
    }

    @AfterEach
    void afterEach() {
        orderService.getOrder(order.getId()).ifPresent(order -> orderService.deleteOrder(order.getId()));
    }

    @Test
    void listAllOrders() {
        Assertions.assertNotNull(orderService.listAllOrders());
    }

    @Test
    @org.junit.jupiter.api.Order(2)
    void getOrder() {
        Optional<Order> optionalOrder = orderService.getOrder(defaultOrderId);
        optionalOrder.ifPresent(order -> {
            Assertions.assertEquals(this.order.getId(), order.getId());
            Assertions.assertNull(order.getClosingDate());
            Assertions.assertEquals(this.order.getClosingPrice(), order.getClosingPrice());
            Assertions.assertEquals(OrderStatus.PENDING, order.getStatus());
        });
        Assertions.assertTrue(optionalOrder.isPresent());
    }

    @Test
    void createOrder() {
        Mockito.doReturn(order).when(orderService)
                .buildOrder();
        Mockito.doReturn(order).when(orderService)
                .saveAndFlush(Mockito.any());
        Assertions.assertNotNull(orderService.createOrder());
    }

    @Test
    void closeOrder() {
        Mockito.doReturn(Optional.ofNullable(order)).when(orderService)
                .getOrder(Mockito.anyInt());
        Mockito.doReturn(order).when(orderStatusService)
                .validateAndChangeOrderToClose(Mockito.any());
        order.setClosingDate(new Date());
        order.calculateClosingPrice();
        order.setStatus(OrderStatus.CLOSED);
        Optional<Order> optionalOrder = Optional.ofNullable(orderService.closeOrder(order.getId()));
        optionalOrder.ifPresent(order -> {
                Assertions.assertEquals(this.order.getId(), order.getId());
                Assertions.assertNotNull(order.getClosingDate());
                Assertions.assertEquals(this.order.getClosingPrice(), order.getClosingPrice());
                Assertions.assertEquals(OrderStatus.CLOSED, order.getStatus());
        });
        Assertions.assertTrue(optionalOrder.isPresent());

    }

    @Test
    @org.junit.jupiter.api.Order(3)
    void deleteOrder() {
        Mockito.doNothing().when(orderStatusService)
                .validateOrderNotClosed(Mockito.any());
        orderService.deleteOrder(defaultOrderId);
        Assertions.assertFalse(orderService.getOrder(order.getId()).isPresent());
    }

    @Test
    @org.junit.jupiter.api.Order(1)
    void saveAndFlush() {
        Assertions.assertNotNull(order);
        Assertions.assertNotNull(order.getId());
        Assertions.assertNull(order.getClosingDate());
        Assertions.assertEquals(0D, order.getClosingPrice());
        Assertions.assertEquals(OrderStatus.PENDING, order.getStatus());
    }

    @Test
    void saveAndFlushEntityActionExceptionTest() {
        Mockito.doNothing().when(orderService)
                .logErrorWrite(Mockito.any());
        Assertions.assertThrows(EntityActionException.class,
                () -> orderService.saveAndFlush(null));
    }

    @Test
    void buildOrder() {
        Order testOrder = orderService.buildOrder();
        Assertions.assertNull(testOrder.getClosingDate());
        Assertions.assertEquals(0D, testOrder.getClosingPrice());
        Assertions.assertEquals(OrderStatus.PENDING, testOrder.getStatus());
        Assertions.assertTrue(testOrder.getOrderLines().isEmpty());
    }
}
