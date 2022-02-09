package com.gabriel.restaurant.order.model.dto.product;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

@Data @Builder
public class Product {

    private Long id;

    @JsonProperty(value = "product_name")
    private String productName;

    @JsonProperty(value = "description")
    private String description;

    @JsonProperty(value = "price")
    private double price;

    @JsonProperty(value = "category_id")
    private Long categoryId;
}
