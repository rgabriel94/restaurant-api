package com.gabriel.restaurant.order.model.dto.orderline;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Getter @Setter @AllArgsConstructor @NoArgsConstructor @Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class OrderLineUpdateRequestDTO {

    @NotNull(message = "Order line id required")
    protected Long id;

    @Min(value = 1, message = "Min quantity value is 1")
    @NotNull(message = "quantity required")
    @JsonProperty(value = "quantity")
    private int quantity;
}
