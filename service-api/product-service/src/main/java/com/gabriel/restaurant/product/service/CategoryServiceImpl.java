package com.gabriel.restaurant.product.service;

import com.gabriel.restaurant.product.entity.Category;
import com.gabriel.restaurant.product.exception.EntityActionException;
import com.gabriel.restaurant.product.exception.NotFoundException;
import com.gabriel.restaurant.product.exception.ResponseException;
import com.gabriel.restaurant.product.interfaces.CategoryService;
import com.gabriel.restaurant.product.repository.CategoryRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Log4j2
@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public List<Category> listAllCategoryOrderByName() {
        return categoryRepository.findAll(Sort.by("categoryName"));
    }

    @Override
    public Optional<Category> getCategory(long categoryId) throws ResponseException {
        return categoryRepository.findById(categoryId);
    }

    @Override
    public Category createCategory(Category category) throws EntityActionException {
        return saveAndFlush(category);
    }

    @Override
    public Category updateCategory(Category category) throws NotFoundException, EntityActionException {
        if (getCategory(category.getId()).isEmpty()) {
            throw new NotFoundException(NotFoundException.CATEGORY_NOT_FOUND);
        }
        return saveAndFlush(category);
    }

    @Override
    public void deleteCategory(long categoryId) throws NotFoundException, EntityActionException {
        if (getCategory(categoryId).isEmpty()) {
            throw new NotFoundException(NotFoundException.CATEGORY_NOT_FOUND);
        }
        categoryRepository.deleteById(categoryId);
    }

    Category saveAndFlush(Category product) throws EntityActionException {
        try {
            return categoryRepository.saveAndFlush(product);
        } catch (Exception e) {
            logErrorWrite(e);
            throw new EntityActionException(EntityActionException.CATEGORY_NOT_SAVE);
        }
    }

    void logErrorWrite(Exception e) {
        log.error(e);
    }
}
