package com.gabriel.restaurant.product.interfaces;

import com.gabriel.restaurant.product.entity.Product;

import java.util.List;
import java.util.Optional;

public interface ProductService {

    List<Product> listAllProductsOrderByCategoryPriorityAsc();

    Optional<Product> getProduct(Long productId);

    Product createProduct(Product product);

    Product updateProduct(Product product);

    void deleteProduct(Long productId);
}
