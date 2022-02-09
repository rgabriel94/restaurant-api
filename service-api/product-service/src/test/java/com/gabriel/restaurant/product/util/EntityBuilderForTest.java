package com.gabriel.restaurant.product.util;

import com.gabriel.restaurant.product.entity.Category;
import com.gabriel.restaurant.product.entity.Product;
import com.gabriel.restaurant.product.model.dto.category.CategoryCreateRequestDTO;
import com.gabriel.restaurant.product.model.dto.category.CategoryResponseDTO;
import com.gabriel.restaurant.product.model.dto.category.CategoryUpdateRequestDTO;
import com.gabriel.restaurant.product.model.dto.product.ProductCreateRequestDTO;
import com.gabriel.restaurant.product.model.dto.product.ProductResponseDTO;
import com.gabriel.restaurant.product.model.dto.product.ProductUpdateRequestDTO;

public interface EntityBuilderForTest {

    Long defaultId = 1L;
    String categoryName = "Drink";
    int priority = 1;

    String productData = "Coca-cola";
    String updateProductData = "Pepsi";
    double price = 1.1;
    double updatePrice = 2.1;


    default Category categoryBuilder() {
        return Category.builder()
                .categoryName(categoryName)
                .priority(priority)
                .build();
    }

    default Category categoryBuilder(Long id) {
        Category category = Category.builder()
                .categoryName(categoryName)
                .priority(priority)
                .build();
        category.setId(id);
        return category;
    }

    default Product productBuilder(Category category) {
        return Product.builder()
                .productName(productData)
                .description(productData)
                .price(price)
                .category(category)
                .build();
    }

    default Product productBuilder(Category category, Long productId) {
        Product product = productBuilder(category);
        product.setId(productId);
        return product;
    }

    default CategoryCreateRequestDTO categoryCreateRequestBuilder() {
        return CategoryCreateRequestDTO.builder()
                .categoryName(categoryName)
                .priority(priority)
                .build();
    }

    default CategoryUpdateRequestDTO categoryUpdateRequestBuilder() {
        return CategoryUpdateRequestDTO.builder()
                .id(defaultId)
                .categoryName(categoryName)
                .priority(priority)
                .build();
    }

    default ProductCreateRequestDTO productCreateRequestBuilder() {
        return ProductCreateRequestDTO.builder()
                .productName(productData)
                .description(productData)
                .price(price)
                .categoryId(defaultId)
                .build();
    }

    default ProductUpdateRequestDTO productUpdateRequestBuilder() {
        return ProductUpdateRequestDTO.builder()
                .id(defaultId)
                .productName(updateProductData)
                .description(updateProductData)
                .price(updatePrice)
                .categoryId(defaultId)
                .build();
    }

    default CategoryResponseDTO categoryResponseBuilder() {
        return CategoryResponseDTO.builder()
                .id(defaultId)
                .categoryName(categoryName)
                .priority(priority)
                .build();
    }

    default ProductResponseDTO productResponseBuilder() {
        return ProductResponseDTO.builder()
                .id(defaultId)
                .productName(productData)
                .description(productData)
                .price(price)
                .categoryId(defaultId)
                .build();
    }

    default ProductResponseDTO productUpdateResponseBuilder() {
        return ProductResponseDTO.builder()
                .id(defaultId)
                .productName(updateProductData)
                .description(updateProductData)
                .price(updatePrice)
                .categoryId(defaultId)
                .build();
    }
}
