package com.gabriel.restaurant.order.model.dto.orderline;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter @Setter @AllArgsConstructor @NoArgsConstructor @Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class OrderLineResponseDTO {

    private Long id;

    @JsonProperty(value = "order_id")
    private Long orderId;

    @JsonProperty(value = "product_id")
    private Long productId;

    @JsonProperty(value = "product_name")
    private String productName;

    @JsonProperty(value = "product_price")
    private double productPrice;

    @JsonProperty(value = "quantity")
    private int quantity;
}
