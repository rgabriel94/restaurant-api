package com.gabriel.restaurant.order.service;

import com.gabriel.restaurant.order.OrderServiceApplication;
import com.gabriel.restaurant.order.client.ProductClient;
import com.gabriel.restaurant.order.entity.Order;
import com.gabriel.restaurant.order.entity.OrderLine;
import com.gabriel.restaurant.order.exception.EntityActionException;
import com.gabriel.restaurant.order.exception.NotFoundException;
import com.gabriel.restaurant.order.interfaces.OrderService;
import com.gabriel.restaurant.order.interfaces.OrderStatusService;
import com.gabriel.restaurant.order.util.EntityBuilderForTest;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;

import java.util.Optional;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest(classes = {OrderServiceApplication.class})
class OrderLineServiceTest implements EntityBuilderForTest {
    private static Order order;

    @SpyBean
    private OrderLineServiceImpl orderLineService;

    @Autowired
    private OrderService orderService;

    @MockBean
    private OrderStatusService orderStatusService;

    @MockBean
    private ProductClient productClient;

    private OrderLine orderLine;
    private Long orderLineId;

    @BeforeAll
    static void beforeAll(@Autowired OrderService orderService) {
        order = orderService.createOrder();
    }

    @AfterAll
    static void afterAll(@Autowired OrderService orderService) {
        orderService.deleteOrder(order.getId());
    }

    @BeforeEach
    void beforeEach() {
        Mockito.doReturn(productBuilder()).when(productClient)
                .getProduct(Mockito.anyLong());
        orderLine = orderLineService.createOrderLine(orderLineBuilder(order));
        orderLineId = orderLine.getId();
    }

    @AfterEach
    void afterEach() {
        orderLineService.getOrderLine(orderLine.getId()).ifPresent(orderLine ->
                orderLineService.deleteOrderLine(orderLine.getId()));

    }

    @Test
    @org.junit.jupiter.api.Order(2)
    void getOrderLineTest() {
        Optional<OrderLine> optionalOrderLine = orderLineService.getOrderLine(orderLineId);
        optionalOrderLine.ifPresent(orderLine -> {
            Assertions.assertEquals(this.orderLine.getId(), orderLine.getId());
            Assertions.assertNotNull(orderLine.getOrder());
            Assertions.assertEquals(defaultId, orderLine.getProductId());
            Assertions.assertEquals(productName, orderLine.getProductName());
            Assertions.assertEquals(productPrice, orderLine.getProductPrice());
            Assertions.assertEquals(quantity, orderLine.getQuantity());
        });
        Assertions.assertTrue(optionalOrderLine.isPresent());
    }

    @Test
    void createOrderLineTest() {
        Mockito.doReturn(orderLine).when(orderLineService)
                .validateOrderNotClosed(Mockito.any());
        Mockito.doNothing().when(orderLineService)
                .addProductData(Mockito.any());
        Mockito.doReturn(orderLine).when(orderLineService)
                .saveAndFlush(Mockito.any());
        Assertions.assertNotNull(orderLineService.createOrderLine(orderLine));
    }

    @Test
    void updateProductQuantityTest() {
        Mockito.doReturn(orderLine).when(orderLineService)
                .validateOrderNotClosed(Mockito.any());
        Mockito.doReturn(orderLine).when(orderLineService)
                .saveAndFlush(Mockito.any());
        OrderLine orderLine = orderLineService.updateProductQuantity(orderLineId, 50);
        Assertions.assertEquals(50, orderLine.getQuantity());
    }

    @Test
    @org.junit.jupiter.api.Order(3)
    void deleteOrderLineTest() {
        orderLineService.deleteOrderLine(orderLine.getId());
        Assertions.assertTrue(orderService.getOrder(orderLine.getId()).isEmpty());
    }

    @Test
    void validateOrderNotClosedTest() {
        Mockito.doNothing().when(orderStatusService)
                .validateOrderNotClosed(Mockito.any());
        Assertions.assertDoesNotThrow(() ->
                orderLineService.validateOrderNotClosed(Optional.ofNullable(orderLine)));
    }

    @Test
    void validateOrderNotClosedNotFoundExceptionTest() {
        Mockito.doNothing().when(orderStatusService)
                .validateOrderNotClosed(Mockito.any());
        Optional<OrderLine> emptyOptional = Optional.ofNullable(null);
        Assertions.assertThrows(NotFoundException.class,
                () -> orderLineService.validateOrderNotClosed(emptyOptional));
    }

    @Test
    void addProductDataTest() {
        Mockito.doReturn(productBuilder()).when(productClient)
                .getProduct(Mockito.anyLong());
        orderLine.setProductName(null);
        orderLine.setProductPrice(0);
        Assertions.assertDoesNotThrow(() -> orderLineService.addProductData(orderLine));
        Assertions.assertEquals(productName, orderLine.getProductName());
        Assertions.assertEquals(productPrice, orderLine.getProductPrice());
    }

    @Test
    void addProductDataExceptionTest() {
        Mockito.doThrow(new RuntimeException()).when(productClient)
                .getProduct(Mockito.anyLong());
        Assertions.assertThrows(NotFoundException.class,
                () -> orderLineService.addProductData(orderLine));
        Assertions.assertEquals(productName, orderLine.getProductName());
        Assertions.assertEquals(productPrice, orderLine.getProductPrice());
    }

    @Test
    @org.junit.jupiter.api.Order(1)
    void saveAndFlushTest() {
        Assertions.assertNotNull(orderLine.getId());
        Assertions.assertNotNull(orderLine.getOrder());
        Assertions.assertEquals(defaultId, orderLine.getProductId());
        Assertions.assertEquals(productName, orderLine.getProductName());
        Assertions.assertEquals(productPrice, orderLine.getProductPrice());
        Assertions.assertEquals(quantity, orderLine.getQuantity());
    }

    @Test
    void saveAndFlushEntityActionExceptionTest() {
        Mockito.doNothing().when(orderLineService)
                .logErrorWrite(Mockito.any());
        Assertions.assertThrows(EntityActionException.class,
                () -> orderLineService.saveAndFlush(null));
    }
}
