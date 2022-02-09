package com.gabriel.restaurant.product.repository;

import com.gabriel.restaurant.product.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {
    
    @Query
    List<Product> findAllByOrderByCategoryPriorityAsc();
}
