package com.gabriel.restaurant.order.controller;

import com.gabriel.restaurant.order.entity.Order;
import com.gabriel.restaurant.order.exception.EntityActionException;
import com.gabriel.restaurant.order.exception.NotFoundException;
import com.gabriel.restaurant.order.exception.NullException;
import com.gabriel.restaurant.order.interfaces.OrderService;
import com.gabriel.restaurant.order.model.dto.order.OrderResponseDTO;
import com.gabriel.restaurant.order.util.EntityBuilderForTest;
import com.gabriel.restaurant.order.util.MapperForTest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import javax.ws.rs.core.MediaType;
import java.util.List;
import java.util.Optional;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class OrderControllerTest implements EntityBuilderForTest, MapperForTest {
    private String urlTemplate = "/api/orders";

    private OrderResponseDTO orderResponseDTO;
    private Order order;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private OrderService orderServiceMock;

    @BeforeEach
    void beforeEach() {
        order = orderBuilder();
        orderResponseDTO = orderResponseBuilder();
    }

    @AfterEach
    void afterEach() {
        order = null;
        orderResponseDTO = null;
    }

    @Test
    void listAllOrdersTest() throws Exception {
        Mockito.when(orderServiceMock.listAllOrders())
                .thenReturn(List.of(order));
        this.mockMvc.perform(MockMvcRequestBuilders.get(urlTemplate))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(mapperListToJson(List.of(orderResponseDTO))));
    }

    @Test
    void getOrderTest() throws Exception {
        Mockito.when(orderServiceMock.getOrder(Mockito.anyLong()))
                .thenReturn(Optional.of(order));
        this.mockMvc.perform(MockMvcRequestBuilders.get(urlTemplate + "/" + defaultId))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(mapperToJson(orderResponseDTO)));
    }

    @Test
    void getOrderNotFoundExceptionTest() throws Exception {
        Mockito.when(orderServiceMock.getOrder(Mockito.anyLong()))
                .thenThrow(new NotFoundException(NotFoundException.ORDER_NOT_FOUND));
        this.mockMvc.perform(MockMvcRequestBuilders.get(urlTemplate + "/" + defaultId))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(content().json(mapperToJson(errorResponseBuilder(NotFoundException.ORDER_NOT_FOUND))));
    }

    @Test
    void createOrderTest() throws Exception {
        Mockito.when(orderServiceMock.createOrder())
                .thenReturn(order);
        this.mockMvc.perform(MockMvcRequestBuilders.post(urlTemplate)
                        .content(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(content().json(mapperToJson(orderResponseDTO)));
    }

    @Test
    void createOrderEntityActionExceptionTest() throws Exception {
        Mockito.when(orderServiceMock.createOrder())
                .thenThrow(new EntityActionException(EntityActionException.ORDER_NOT_SAVE));
        this.mockMvc.perform(MockMvcRequestBuilders.post(urlTemplate)
                        .content(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().json(mapperToJson(errorResponseBuilder(EntityActionException.ORDER_NOT_SAVE))));
    }


    @Test
    void closeOrderTest() throws Exception {
        Mockito.when(orderServiceMock.closeOrder(Mockito.anyLong()))
                .thenReturn(order);
        this.mockMvc.perform(MockMvcRequestBuilders.put(urlTemplate + "/closed/" + defaultId)
                        .content(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(mapperToJson(orderResponseDTO)));
    }

    @Test
    void closeOrderNullExceptionTest() throws Exception {
        Mockito.when(orderServiceMock.closeOrder(Mockito.anyLong()))
                .thenThrow(new NullException(NullException.ORDER_NULL));
        this.mockMvc.perform(MockMvcRequestBuilders.put(urlTemplate + "/closed/" + defaultId)
                        .content(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().json(mapperToJson(errorResponseBuilder(NullException.ORDER_NULL))));
    }

    @Test
    void closeOrderEntityActionExceptionClosedTest() throws Exception {
        Mockito.when(orderServiceMock.closeOrder(Mockito.anyLong()))
                .thenThrow(new EntityActionException(EntityActionException.ORDER_CLOSED));
        this.mockMvc.perform(MockMvcRequestBuilders.put(urlTemplate + "/closed/" + defaultId)
                        .content(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().json(mapperToJson(errorResponseBuilder(EntityActionException.ORDER_CLOSED))));
    }

    @Test
    void closeOrderEntityActionExceptionTest() throws Exception {
        Mockito.when(orderServiceMock.closeOrder(Mockito.anyLong()))
                .thenThrow(new EntityActionException(EntityActionException.ORDER_EMPTY));
        this.mockMvc.perform(MockMvcRequestBuilders.put(urlTemplate + "/closed/" + defaultId)
                        .content(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().json(mapperToJson(errorResponseBuilder(EntityActionException.ORDER_EMPTY))));
    }

    @Test
    void deleteOrderTest() throws Exception {
        Mockito.doNothing().when(orderServiceMock)
                .deleteOrder(Mockito.anyLong());
        this.mockMvc.perform(MockMvcRequestBuilders.delete(urlTemplate + "/" + defaultId))
                .andDo(print())
                .andExpect(status().isNoContent());
    }

    @Test
    void deleteOrderLineNullExceptionTest() throws Exception {
        Mockito.doThrow(new NullException(NullException.ORDER_NULL)).when(orderServiceMock)
                .deleteOrder(Mockito.anyLong());
        this.mockMvc.perform(MockMvcRequestBuilders.delete(urlTemplate + "/" + defaultId))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().json(mapperToJson(errorResponseBuilder(NullException.ORDER_NULL))));
    }

    @Test
    void deleteOrderLineEntityActionExceptionTest() throws Exception {
        Mockito.doThrow(new EntityActionException(String.format(EntityActionException.ORDER_CLOSED, defaultId))).when(orderServiceMock)
                .deleteOrder(Mockito.anyLong());
        this.mockMvc.perform(MockMvcRequestBuilders.delete(urlTemplate + "/" + defaultId))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().json(mapperToJson(errorResponseBuilder(String.format(EntityActionException.ORDER_CLOSED, defaultId)))));
    }
}
