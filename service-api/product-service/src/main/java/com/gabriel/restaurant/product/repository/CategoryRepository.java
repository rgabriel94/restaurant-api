package com.gabriel.restaurant.product.repository;

import com.gabriel.restaurant.product.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {


}
