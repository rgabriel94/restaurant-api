package com.gabriel.restaurant.order.model.dto.error;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

@Getter @Setter @AllArgsConstructor @NoArgsConstructor @Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class ErrorResponseDTO {

    private String message;
}
