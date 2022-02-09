package com.gabriel.restaurant.order.client;

import com.gabriel.restaurant.order.model.dto.product.Product;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

@FeignClient(name = "product-service")
@RequestMapping("/products")
public interface ProductClient {

    @GetMapping(value = "/{productId}")
    @ResponseStatus(HttpStatus.OK)
    Product getProduct(@PathVariable Long productId);
}
