package com.gabriel.restaurant.product.service;

import com.gabriel.restaurant.product.ProductServiceApplication;
import com.gabriel.restaurant.product.entity.Category;
import com.gabriel.restaurant.product.entity.Product;
import com.gabriel.restaurant.product.exception.EntityActionException;
import com.gabriel.restaurant.product.exception.NotFoundException;
import com.gabriel.restaurant.product.interfaces.CategoryService;
import com.gabriel.restaurant.product.util.EntityBuilderForTest;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;

import java.util.Optional;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest(classes = {ProductServiceApplication.class})
class ProductServiceTest implements EntityBuilderForTest {
    private static Category category;

    @SpyBean
    private ProductServiceImpl productService;

    @Autowired
    private CategoryService categoryService;

    private Product product;

    @BeforeAll
    static void beforeAll(@Autowired CategoryService categoryService) {
        Category tmpCategory = Category.builder()
                .categoryName(categoryName)
                .priority(priority)
                .build();
        category = categoryService.createCategory(tmpCategory);
    }

    @AfterAll
    static void afterAll(@Autowired CategoryService categoryService) {
        categoryService.deleteCategory(category.getId());
    }

    @BeforeEach
    void beforeEach() {
        product = productService.createProduct(productBuilder(category));
    }

    @AfterEach
    void afterEach() {
        productService.deleteProduct(product.getId());
    }

    @Test
    void listAllProductsOrderByCategoryPriorityAscTest() {
        Assertions.assertNotNull(productService.listAllProductsOrderByCategoryPriorityAsc());
    }

    @Test
    @Order(2)
    void getProductTest() {
        Optional<Product> optionalProduct = productService.getProduct(product.getId());
        optionalProduct.ifPresent(product -> {
            Assertions.assertEquals(this.product.getId(), product.getId());
            Assertions.assertEquals(this.product.getProductName(), product.getProductName());
            Assertions.assertEquals(this.product.getDescription(), product.getDescription());
            Assertions.assertEquals(this.product.getPrice(), product.getPrice());
            Assertions.assertEquals(this.product.getCategory().getId(), product.getCategory().getId());
        });
        Assertions.assertTrue(optionalProduct.isPresent());
    }

    @Test
    void getProductWithoutResultTest() {
        Optional<Product> optionalProduct = productService.getProduct(-defaultId);
        Assertions.assertFalse(optionalProduct.isPresent());
    }

    @Test
    void getProductResponseException() {
        Assertions.assertThrows(EntityActionException.class,
                () -> productService.getProduct(null));
    }

    @Test
    @Order(1)
    void createProductTest() {
        Assertions.assertNotNull(product);
        Assertions.assertNotNull(product.getId());
        Assertions.assertNotNull(product.getCategory());
        Assertions.assertEquals(productData, product.getProductName());
        Assertions.assertEquals(productData, product.getDescription());
        Assertions.assertEquals(price, product.getPrice());
        Assertions.assertEquals(category.getId(), product.getCategory().getId());
    }

    @Test
    void createProductEntityActionExceptionTest() {
        Mockito.doNothing().when(productService)
                .logErrorWrite(Mockito.any());
        Product product = Product.builder().build();
        Assertions.assertThrows(EntityActionException.class,
                () -> productService.createProduct(product));
    }

    @Test
    void updateProductTest() {
        Category categoryForUpdate = categoryBuilder();
        updateProduct(product, categoryService.createCategory(categoryForUpdate));
        Product updatedProduct = productService.updateProduct(product);
        Assertions.assertNotNull(updatedProduct);
        Assertions.assertNotNull(updatedProduct.getCategory());
        Assertions.assertEquals(product.getId(), updatedProduct.getId());
        Assertions.assertEquals(updateProductData, updatedProduct.getProductName());
        Assertions.assertEquals(updateProductData, updatedProduct.getDescription());
        Assertions.assertEquals(price + 1, updatedProduct.getPrice());
        Assertions.assertNotEquals(this.category.getId(), updatedProduct.getCategory().getId());
        updatedProduct.setCategory(category);
        productService.updateProduct(updatedProduct);
        categoryService.deleteCategory(categoryForUpdate.getId());
    }

    @Test
    void updateProductNotFoundExceptionTest() {
        Product tmpProduct = Product.builder().id(defaultId).build();
        Assertions.assertThrows(NotFoundException.class,
                () -> productService.updateProduct(tmpProduct));
    }

    @Test
    void updateProductEntityActionExceptionNotIdTest() {
        Product tmpProduct = Product.builder().build();
        Assertions.assertThrows(EntityActionException.class,
                () -> productService.updateProduct(tmpProduct));
    }

    @Test
    void updateProductEntityActionExceptionNotSaveTest() {
        Mockito.doNothing().when(productService)
                .logErrorWrite(Mockito.any());
        Product tmpProduct = Product.builder().id(product.getId()).build();
        Assertions.assertThrows(EntityActionException.class,
                () -> productService.updateProduct(tmpProduct));
    }

    @Test
    @Order(3)
    void deleteProductTest() {
        Product product = productService.createProduct(productBuilder(category));
        Assertions.assertNotNull(product);
        productService.deleteProduct(product.getId());
        Assertions.assertFalse(productService.getProduct(product.getId()).isPresent());
    }

    @Test
    void deleteProductEntityActionExceptionTest() {
        Assertions.assertThrows(EntityActionException.class,
                () -> productService.deleteProduct(null));
    }

    private void updateProduct(Product product, Category categoryForUpdate) {
        product.setProductName(updateProductData);
        product.setDescription(updateProductData);
        product.setPrice(price + 1);
        product.setCategory(categoryForUpdate);
    }
}

