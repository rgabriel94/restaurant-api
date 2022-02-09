package com.gabriel.restaurant.order.model.dto.order;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.gabriel.restaurant.order.enumeration.OrderStatus;
import com.gabriel.restaurant.order.model.dto.orderline.OrderLineResponseDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter @Setter @AllArgsConstructor @NoArgsConstructor @Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class OrderResponseDTO {

    private Long id;

    @JsonProperty(value = "closing_date")
    private Date closingDate;

    @JsonProperty(value = "closing_price")
    private double closingPrice;

    @JsonProperty(value = "status")
    private OrderStatus status;

    @JsonProperty(value = "order_lines")
    private List<OrderLineResponseDTO> orderLines;
}
