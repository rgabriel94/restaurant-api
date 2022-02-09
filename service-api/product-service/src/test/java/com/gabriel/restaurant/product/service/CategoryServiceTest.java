package com.gabriel.restaurant.product.service;

import com.gabriel.restaurant.product.ProductServiceApplication;
import com.gabriel.restaurant.product.entity.Category;
import com.gabriel.restaurant.product.exception.EntityActionException;
import com.gabriel.restaurant.product.exception.NotFoundException;
import com.gabriel.restaurant.product.util.EntityBuilderForTest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;

import java.util.Optional;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest(classes = {ProductServiceApplication.class})
class CategoryServiceTest implements EntityBuilderForTest {

    @SpyBean
    private CategoryServiceImpl categoryService;

    private Category category;

    @BeforeEach
    void beforeEach() {
        category = categoryService.createCategory(categoryBuilder());
    }

    @AfterEach
    void afterEach() {
        categoryService.getCategory(category.getId()).ifPresent(category ->
                categoryService.deleteCategory(category.getId()));
    }

    @Test
    void listAllCategoryOrderByNameTest() {
        Assertions.assertNotNull(categoryService.listAllCategoryOrderByName());
    }

    @Test
    @Order(2)
    void getCategoryTest() {
        Optional<Category> optionalProduct = categoryService.getCategory(category.getId());
        optionalProduct.ifPresent(category -> {
            Assertions.assertEquals(this.category.getId(), category.getId());
            Assertions.assertEquals(this.category.getCategoryName(), category.getCategoryName());
            Assertions.assertEquals(this.category.getPriority(), category.getPriority());
        });
        Assertions.assertTrue(optionalProduct.isPresent());
    }

    @Test
    void getCategoryWithoutResultTest() {
        Optional<Category> optionalProduct = categoryService.getCategory(-defaultId);
        Assertions.assertFalse(optionalProduct.isPresent());
    }

    @Test
    @Order(1)
    void createCategoryTest() {
        Mockito.doThrow(new EntityActionException(EntityActionException.CATEGORY_NOT_SAVE)).when(categoryService)
                .saveAndFlush(Mockito.any());
        Assertions.assertThrows(EntityActionException.class,
                () -> categoryService.createCategory(category));
    }

    @Test
    void createCategoryEntityActionExceptionTest() {
        Mockito.doThrow(new EntityActionException(EntityActionException.CATEGORY_NOT_SAVE)).when(categoryService)
                .saveAndFlush(Mockito.any());
        Assertions.assertThrows(EntityActionException.class,
                () -> categoryService.createCategory(category));
    }

    @Test
    void updateCategoryTest() {
        Mockito.doReturn(Optional.of(category)).when(categoryService)
                .getCategory(Mockito.anyLong());
        Mockito.doReturn(category).when(categoryService)
                .saveAndFlush(Mockito.any());
        Assertions.assertNotNull(categoryService.updateCategory(category));
    }

    @Test
    void updateCategoryNotFoundExceptionTest() {
        Mockito.doReturn(Optional.ofNullable(null)).when(categoryService)
                .getCategory(Mockito.anyLong());
        Category category = Category.builder().id(defaultId).build();
        Assertions.assertThrows(NotFoundException.class,
                () -> categoryService.updateCategory(category));
    }

    @Test
    void updateCategoryEntityActionExceptionNotSaveTest() {
        Mockito.doReturn(Optional.ofNullable(category)).when(categoryService)
                .getCategory(Mockito.anyLong());
        Mockito.doThrow(new EntityActionException(EntityActionException.CATEGORY_NOT_SAVE)).when(categoryService)
                .saveAndFlush(Mockito.any());
        Assertions.assertThrows(EntityActionException.class,
                () -> categoryService.updateCategory(category));
    }

    @Test
    @Order(3)
    void deleteCategoryTest() {
        categoryService.deleteCategory(category.getId());
        Assertions.assertTrue(categoryService.getCategory(category.getId()).isEmpty());
    }

    @Test
    void deleteCategoryEntityActionExceptionTest() {
        Mockito.doReturn(Optional.ofNullable(null)).when(categoryService)
                .getCategory(Mockito.anyLong());
        Assertions.assertThrows(NotFoundException.class,
                () -> categoryService.deleteCategory(defaultId));
    }

    @Test
    void saveAndFlush() {
        Assertions.assertNotNull(category);
        Assertions.assertNotNull(category.getId());
        Assertions.assertEquals(categoryName, category.getCategoryName());
        Assertions.assertEquals(priority, category.getPriority());
    }

    @Test
    void saveAndFlushEntityActionException() {
        Mockito.doNothing().when(categoryService)
                .logErrorWrite(Mockito.any());
        Assertions.assertThrows(EntityActionException.class,
                () -> categoryService.saveAndFlush(null));
    }

    @Test
    void logErrorWrite() {
        Assertions.assertDoesNotThrow(() ->
                categoryService.logErrorWrite(new NotFoundException(NotFoundException.CATEGORY_NOT_FOUND)));
    }
}
