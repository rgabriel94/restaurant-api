package com.gabriel.restaurant.order.controller;

import com.gabriel.restaurant.order.entity.Order;
import com.gabriel.restaurant.order.entity.OrderLine;
import com.gabriel.restaurant.order.exception.EntityActionException;
import com.gabriel.restaurant.order.exception.NotFoundException;
import com.gabriel.restaurant.order.exception.NullException;
import com.gabriel.restaurant.order.interfaces.OrderLineService;
import com.gabriel.restaurant.order.model.dto.orderline.OrderLineResponseDTO;
import com.gabriel.restaurant.order.model.dto.orderline.OrderLineUpdateRequestDTO;
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
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class OrderLineControllerTest implements EntityBuilderForTest, MapperForTest {
    private String urlTemplate = "/api/order/lines";

    private OrderLineUpdateRequestDTO orderLineUpdateRequestDTO;
    private OrderLineResponseDTO orderLineResponseDTO;
    private OrderLine orderLine;
    private Order order;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private OrderLineService orderLineServiceMock;

    @BeforeEach
    void beforeEach() {
        order = orderBuilder();
        order.setId(defaultId);
        orderLine = orderLineBuilder(order, defaultId);
        orderLineUpdateRequestDTO = orderLineUpdateRequestBuilder();
        orderLineResponseDTO = orderLineResponseBuilder();
    }

    @AfterEach
    void afterEach() {
        order = null;
        orderLine = null;
        orderLineResponseDTO = null;
    }

    @Test
    void createOrderLineTest() throws Exception {
        Mockito.when(orderLineServiceMock.createOrderLine(Mockito.any()))
                .thenReturn(orderLine);
        this.mockMvc.perform(MockMvcRequestBuilders.post(urlTemplate)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapperToJson(orderLineCreateRequestBuilder())))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(content().json(mapperToJson(orderLineResponseDTO)));
    }

    @Test
    void createOrderLineNotFoundExceptionTest() throws Exception {
        Mockito.when(orderLineServiceMock.createOrderLine(Mockito.any()))
                .thenThrow(new NotFoundException(NotFoundException.ORDER_LINE_NOT_FOUND));
        this.mockMvc.perform(MockMvcRequestBuilders.post(urlTemplate)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapperToJson(orderLineCreateRequestBuilder())))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(content().json(mapperToJson(errorResponseBuilder(NotFoundException.ORDER_LINE_NOT_FOUND))));
    }

    @Test
    void createOrderLineNullExceptionTest() throws Exception {
        Mockito.when(orderLineServiceMock.createOrderLine(Mockito.any()))
                .thenThrow(new NullException(NullException.ORDER_NULL));
        this.mockMvc.perform(MockMvcRequestBuilders.post(urlTemplate)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapperToJson(orderLineCreateRequestBuilder())))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().json(mapperToJson(errorResponseBuilder(NullException.ORDER_NULL))));
    }

    @Test
    void createOrderLineEntityActionExceptionTest() throws Exception {
        Mockito.when(orderLineServiceMock.createOrderLine(Mockito.any()))
                .thenThrow(new EntityActionException(EntityActionException.ORDER_CLOSED));
        this.mockMvc.perform(MockMvcRequestBuilders.post(urlTemplate)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapperToJson(orderLineCreateRequestBuilder())))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().json(mapperToJson(errorResponseBuilder(EntityActionException.ORDER_CLOSED))));
    }

    @Test
    void updateProductQuantityTest() throws Exception {
        Mockito.when(orderLineServiceMock.updateProductQuantity(Mockito.anyLong(), Mockito.anyInt()))
                .thenReturn(orderLine);
        this.mockMvc.perform(MockMvcRequestBuilders.put(urlTemplate)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(mapperToJson(orderLineUpdateRequestBuilder())))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(mapperToJson(orderLineResponseDTO)));
    }

    @Test
    void updateProductQuantityResponseExceptionTest() throws Exception {
        Mockito.when(orderLineServiceMock.updateProductQuantity(Mockito.anyLong(), Mockito.anyInt()))
                .thenReturn(orderLine);
        this.mockMvc.perform(MockMvcRequestBuilders.put(urlTemplate)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapperToJson(OrderLineUpdateRequestDTO.builder().build())))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    void updateProductQuantityNotFoundExceptionTest() throws Exception {
        Mockito.when(orderLineServiceMock.updateProductQuantity(Mockito.anyLong(), Mockito.anyInt()))
                .thenThrow(new NotFoundException(NotFoundException.ORDER_LINE_NOT_FOUND));
        this.mockMvc.perform(MockMvcRequestBuilders.put(urlTemplate)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapperToJson(orderLineUpdateRequestBuilder())))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(content().json(mapperToJson(errorResponseBuilder(NotFoundException.ORDER_LINE_NOT_FOUND))));
    }

    @Test
    void updateProductQuantityEntityActionExceptionExceptionTest() throws Exception {
        Mockito.when(orderLineServiceMock.updateProductQuantity(Mockito.anyLong(), Mockito.anyInt()))
                .thenThrow(new EntityActionException(String.format(EntityActionException.ORDER_CLOSED, defaultId)));
        this.mockMvc.perform(MockMvcRequestBuilders.put(urlTemplate)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapperToJson(orderLineUpdateRequestBuilder())))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().json(mapperToJson(errorResponseBuilder(String.format(EntityActionException.ORDER_CLOSED, defaultId)))));
    }

    @Test
    void deleteOrderLineTest() throws Exception {
        Mockito.doNothing().when(orderLineServiceMock)
                .deleteOrderLine(Mockito.anyLong());
        this.mockMvc.perform(MockMvcRequestBuilders.delete(urlTemplate + "/" + defaultId))
                .andDo(print())
                .andExpect(status().isNoContent());
    }

    @Test
    void deleteOrderLineNotFoundExceptionTest() throws Exception {
        Mockito.doThrow(new NotFoundException(NotFoundException.ORDER_LINE_NOT_FOUND)).when(orderLineServiceMock)
                .deleteOrderLine(Mockito.anyLong());
        this.mockMvc.perform(MockMvcRequestBuilders.delete(urlTemplate + "/" + defaultId))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(content().json(mapperToJson(errorResponseBuilder(NotFoundException.ORDER_LINE_NOT_FOUND))));
    }

    @Test
    void deleteOrderLineEntityActionExceptionTest() throws Exception {
        Mockito.doThrow(new EntityActionException(String.format(EntityActionException.ORDER_CLOSED, order.getId()))).when(orderLineServiceMock)
                .deleteOrderLine(Mockito.anyLong());
        this.mockMvc.perform(MockMvcRequestBuilders.delete(urlTemplate + "/" + defaultId))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().json(mapperToJson(errorResponseBuilder(String.format(EntityActionException.ORDER_CLOSED, defaultId)))));
    }
}
