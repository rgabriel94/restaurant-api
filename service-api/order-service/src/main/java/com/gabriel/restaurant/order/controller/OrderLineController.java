package com.gabriel.restaurant.order.controller;

import com.gabriel.restaurant.order.entity.OrderLine;
import com.gabriel.restaurant.order.interfaces.OrderLineService;
import com.gabriel.restaurant.order.model.dto.orderline.OrderLineCreateRequestDTO;
import com.gabriel.restaurant.order.model.dto.orderline.OrderLineResponseDTO;
import com.gabriel.restaurant.order.model.dto.orderline.OrderLineUpdateRequestDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "api/order/lines")
public class OrderLineController extends BaseController {

    @Autowired
    OrderLineService orderLineService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public OrderLineResponseDTO createOrderLine(@Valid @RequestBody OrderLineCreateRequestDTO orderLineRequestDTO, BindingResult bindingResult) {
        bindingResultService.bindingResultErrors(bindingResult);
        OrderLine orderLine = mapper.convert(orderLineRequestDTO, OrderLine.class);
        return mapper.convert(orderLineService.createOrderLine(orderLine), OrderLineResponseDTO.class);
    }

    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public OrderLineResponseDTO updateProductQuantity(@Valid @RequestBody OrderLineUpdateRequestDTO orderLineRequestDTO, BindingResult bindingResult) {
        bindingResultService.bindingResultErrors(bindingResult);
        OrderLine orderLine = orderLineService.updateProductQuantity(orderLineRequestDTO.getId(), orderLineRequestDTO.getQuantity());
        return mapper.convert(orderLine, OrderLineResponseDTO.class);
    }

    @DeleteMapping(value = "/{orderLineId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteOrderLine(@PathVariable Long orderLineId) {
        orderLineService.deleteOrderLine(orderLineId);
    }
}
