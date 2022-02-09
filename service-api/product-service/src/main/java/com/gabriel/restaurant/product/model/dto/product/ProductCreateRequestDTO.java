package com.gabriel.restaurant.product.model.dto.product;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter @Setter @AllArgsConstructor @NoArgsConstructor @Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class ProductCreateRequestDTO {

    @NotBlank(message = "Product name required.")
    @Size(max = 255, message = "Max product names size is 255 characters.")
    @JsonProperty(value = "product_name")
    private String productName;

    @Size(max = 255, message = "Max description size is 255 characters.")
    @JsonProperty(value = "description")
    private String description;

    @Min(value = 0, message = "Min price value is 0")
    @JsonProperty(value = "price")
    private double price;

    @NotNull(message = "Category id required")
    @JsonProperty(value = "category_id")
    private Long categoryId;
}
