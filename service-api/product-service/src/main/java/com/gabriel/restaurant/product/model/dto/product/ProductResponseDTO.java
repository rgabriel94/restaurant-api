package com.gabriel.restaurant.product.model.dto.product;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter @Setter @AllArgsConstructor @NoArgsConstructor @Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class ProductResponseDTO {

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
