package com.gabriel.restaurant.order.service;

import com.gabriel.restaurant.order.client.ProductClient;
import com.gabriel.restaurant.order.entity.OrderLine;
import com.gabriel.restaurant.order.exception.EntityActionException;
import com.gabriel.restaurant.order.exception.NotFoundException;
import com.gabriel.restaurant.order.interfaces.OrderLineService;
import com.gabriel.restaurant.order.interfaces.OrderStatusService;
import com.gabriel.restaurant.order.model.dto.product.Product;
import com.gabriel.restaurant.order.repository.OrderLineRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Log4j2
@Service
public class OrderLineServiceImpl implements OrderLineService {

    @Autowired
    OrderLineRepository orderLineRepository;

    @Autowired
    OrderStatusService orderStatusService;

    @Autowired
    ProductClient productClient;

    @Override
    public Optional<OrderLine> getOrderLine(long orderLineId) {
        return orderLineRepository.findById(orderLineId);
    }

    @Override
    public OrderLine createOrderLine(OrderLine orderLine) {
        validateOrderNotClosed(Optional.ofNullable(orderLine));
        addProductData(orderLine);
        return saveAndFlush(orderLine);
    }

    @Override
    public OrderLine updateProductQuantity(long orderLineId, int quantity) {
        OrderLine orderLine = validateOrderNotClosed(getOrderLine(orderLineId));
        orderLine.setQuantity(quantity);
        return saveAndFlush(orderLine);
    }

    @Override
    public void deleteOrderLine(long orderLineId) {
        validateOrderNotClosed(getOrderLine(orderLineId));
        orderLineRepository.deleteById(orderLineId);
    }

    OrderLine validateOrderNotClosed(Optional<OrderLine> optionalOrderLine) {
        OrderLine orderLine = optionalOrderLine.orElseThrow(
                () -> new NotFoundException(NotFoundException.ORDER_LINE_NOT_FOUND));
        orderStatusService.validateOrderNotClosed(Optional.ofNullable(orderLine.getOrder()));
        return orderLine;
    }

    void addProductData(OrderLine orderLine) {
        try {
            Product product = productClient.getProduct(orderLine.getProductId());
            orderLine.setProductName(product.getProductName());
            orderLine.setProductPrice(product.getPrice());
        } catch (Exception e) {
            logErrorWrite(e);
            throw new NotFoundException(NotFoundException.PRODUCT_NOT_FOUND);
        }
    }

    OrderLine saveAndFlush(OrderLine order) {
        try {
            return orderLineRepository.saveAndFlush(order);
        } catch (Exception e) {
            logErrorWrite(e);
            throw new EntityActionException(EntityActionException.ORDER_NOT_SAVE);
        }
    }

    void logErrorWrite(Exception e) {
        log.error(e);
    }
}
