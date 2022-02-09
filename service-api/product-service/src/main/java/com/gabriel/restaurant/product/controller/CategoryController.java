package com.gabriel.restaurant.product.controller;

import com.gabriel.restaurant.product.entity.Category;
import com.gabriel.restaurant.product.exception.NotFoundException;
import com.gabriel.restaurant.product.interfaces.CategoryService;
import com.gabriel.restaurant.product.model.dto.category.CategoryCreateRequestDTO;
import com.gabriel.restaurant.product.model.dto.category.CategoryResponseDTO;
import com.gabriel.restaurant.product.model.dto.category.CategoryUpdateRequestDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value = "categories")
public class CategoryController extends BaseController {

    @Autowired
    CategoryService categoryService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<CategoryResponseDTO> listAllCategoryOrderByName() {
        List<Category> categories = categoryService.listAllCategoryOrderByName();
        return mapper.convert(categories, CategoryResponseDTO.class);
    }

    @GetMapping(value = "/{categoryId}")
    @ResponseStatus(HttpStatus.OK)
    public CategoryResponseDTO getCategory(@PathVariable Long categoryId) {
        Category category = categoryService.getCategory(categoryId).orElseThrow(() -> new NotFoundException(NotFoundException.CATEGORY_NOT_FOUND));
        return mapper.convert(category, CategoryResponseDTO.class);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CategoryResponseDTO createCategory(@Valid @RequestBody CategoryCreateRequestDTO categoryRequestDTO, BindingResult bindingResult) {
        bindingResultService.bindingResultErrors(bindingResult);
        Category category = mapper.convert(categoryRequestDTO, Category.class);
        return mapper.convert(categoryService.createCategory(category), CategoryResponseDTO.class);
    }

    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public CategoryResponseDTO updateCategory(@Valid @RequestBody CategoryUpdateRequestDTO categoryRequestDTO, BindingResult bindingResult) {
        bindingResultService.bindingResultErrors(bindingResult);
        Category category = mapper.convert(categoryRequestDTO, Category.class);
        return mapper.convert(categoryService.updateCategory(category), CategoryResponseDTO.class);
    }

    @DeleteMapping("/{categoryId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCategory(@PathVariable long categoryId) {
        categoryService.deleteCategory(categoryId);
    }
}
