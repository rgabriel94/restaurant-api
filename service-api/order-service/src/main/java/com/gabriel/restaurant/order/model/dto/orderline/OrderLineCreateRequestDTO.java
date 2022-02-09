package com.gabriel.restaurant.order.model.dto.orderline;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Getter @Setter @AllArgsConstructor @NoArgsConstructor @Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class OrderLineCreateRequestDTO {

    @NotNull(message = "Order line id required")
    private Long id;

    @NotNull(message = "Order id required")
    @JsonProperty(value = "order_id")
    private Long orderId;

    @NotNull(message = "Product id required")
    @JsonProperty(value = "product_id")
    private Long productId;

    @Min(value = 1, message = "Min quantity value is 1")
    @JsonProperty(value = "quantity")
    private int quantity;
}
