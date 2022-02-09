package com.gabriel.restaurant.product.interfaces;

import com.gabriel.restaurant.product.entity.Category;
import com.gabriel.restaurant.product.exception.ResponseException;

import java.util.List;
import java.util.Optional;

public interface CategoryService {

    List<Category> listAllCategoryOrderByName();

    Optional<Category> getCategory(long categoryId) throws ResponseException;

    Category createCategory(Category category);

    Category updateCategory(Category category);

    void deleteCategory(long categoryId);
}
