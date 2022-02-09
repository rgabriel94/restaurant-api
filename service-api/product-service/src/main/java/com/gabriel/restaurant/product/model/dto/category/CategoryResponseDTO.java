package com.gabriel.restaurant.product.model.dto.category;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter @Setter @AllArgsConstructor @NoArgsConstructor @Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class CategoryResponseDTO {

    private Long id;

    @JsonProperty(value = "category_name")
    private String categoryName;

    @JsonProperty(value = "priority")
    private int priority;
}
