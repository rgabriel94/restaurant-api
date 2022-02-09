package com.gabriel.restaurant.product.model.dto.category;

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
public class CategoryUpdateRequestDTO {

    @NotNull(message = "Category id required")
    public Long id;

    @NotBlank(message = "Category name required.")
    @Size(max = 255, message = "Max category names size is 255 characters.")
    @JsonProperty(value = "category_name")
    private String categoryName;

    @Min(value = 1, message = "Min priority value is 1.")
    @JsonProperty(value = "priority")
    private int priority;
}
