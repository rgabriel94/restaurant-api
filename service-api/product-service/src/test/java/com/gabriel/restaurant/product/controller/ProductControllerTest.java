package com.gabriel.restaurant.product.controller;

import com.gabriel.restaurant.product.entity.Category;
import com.gabriel.restaurant.product.entity.Product;
import com.gabriel.restaurant.product.exception.EntityActionException;
import com.gabriel.restaurant.product.exception.NotFoundException;
import com.gabriel.restaurant.product.interfaces.ProductService;
import com.gabriel.restaurant.product.model.dto.category.CategoryCreateRequestDTO;
import com.gabriel.restaurant.product.model.dto.product.ProductCreateRequestDTO;
import com.gabriel.restaurant.product.model.dto.product.ProductResponseDTO;
import com.gabriel.restaurant.product.model.dto.product.ProductUpdateRequestDTO;
import com.gabriel.restaurant.product.util.EntityBuilderForTest;
import com.gabriel.restaurant.product.util.MapperForTest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;
import java.util.Optional;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class ProductControllerTest implements EntityBuilderForTest, MapperForTest {
    String urlTemplate = "/products";
    String urlTemplateWithId = "/products/" + defaultId;

    private ProductCreateRequestDTO productCreateRequest;
    private ProductUpdateRequestDTO productUpdateRequest;
    private ProductResponseDTO productResponse;
    private Product product;
    private Category category;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductService productServiceMock;

    @BeforeEach
    void beforeEach() {
        category = categoryBuilder(defaultId);
        product = productBuilder(category, defaultId);
        productCreateRequest = productCreateRequestBuilder();
        productUpdateRequest = productUpdateRequestBuilder();
        productResponse = productResponseBuilder();
    }

    @AfterEach
    void afterEach() {
        category = null;
        product = null;
        productCreateRequest = null;
        productUpdateRequest = null;
        productResponse = null;
    }

    @Test
    void listAllProductOrderByNameTest() throws Exception {
        Mockito.when(productServiceMock.listAllProductsOrderByCategoryPriorityAsc())
                .thenReturn(List.of(product));
        this.mockMvc.perform(MockMvcRequestBuilders.get(urlTemplate))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(mapperListToJson(List.of(productResponse))));
    }

    @Test
    void getProductTest() throws Exception {
        Mockito.when(productServiceMock.getProduct(Mockito.any()))
                .thenReturn(Optional.of(product));
        this.mockMvc.perform(MockMvcRequestBuilders.get(urlTemplateWithId))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(mapperToJson(productResponse)));
    }

    @Test
    void getProductNotFoundTest() throws Exception {
        Mockito.when(productServiceMock.getProduct(Mockito.any()))
                .thenReturn(Optional.empty());
        this.mockMvc.perform(MockMvcRequestBuilders.get(urlTemplateWithId))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(content().json(mapperToJson(errorResponseBuilder(NotFoundException.PRODUCT_NOT_FOUND))));
    }

    @Test
    void getProductEntityActionExceptionNotIdTest() throws Exception {
        Mockito.when(productServiceMock.getProduct(Mockito.any()))
                .thenThrow(new EntityActionException(EntityActionException.PRODUCT_ID_NULL));
        this.mockMvc.perform(MockMvcRequestBuilders.get(urlTemplateWithId))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().json(mapperToJson(errorResponseBuilder(EntityActionException.PRODUCT_ID_NULL))));
    }

    @Test
    void createProductTest() throws Exception {
        Mockito.when(productServiceMock.createProduct(Mockito.any()))
                .thenReturn(product);
        this.mockMvc.perform(MockMvcRequestBuilders.post(urlTemplate)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapperToJson(productCreateRequest)))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(content().json(mapperToJson(productResponse)));
    }

    @Test
    void createProductResponseExceptionTest() throws Exception {
        Mockito.when(productServiceMock.createProduct(Mockito.any()))
                .thenReturn(product);
        this.mockMvc.perform(MockMvcRequestBuilders.post(urlTemplate)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapperToJson(ProductCreateRequestDTO.builder().build())))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    void updateProductTest() throws Exception {
        Mockito.when(productServiceMock.updateProduct(Mockito.any()))
                .thenReturn(product);
        this.mockMvc.perform(MockMvcRequestBuilders.put(urlTemplate)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapperToJson(productUpdateRequest)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(mapperToJson(productResponse)));
    }

    @Test
    void updateProductResponseExceptionTest() throws Exception {
        Mockito.when(productServiceMock.updateProduct(Mockito.any()))
                .thenReturn(product);
        this.mockMvc.perform(MockMvcRequestBuilders.put(urlTemplate)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapperToJson(CategoryCreateRequestDTO.builder().build())))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    void updateProductNotFoundTest() throws Exception {
        Mockito.when(productServiceMock.updateProduct(Mockito.any()))
                .thenThrow(new NotFoundException(NotFoundException.PRODUCT_NOT_FOUND));
        this.mockMvc.perform(MockMvcRequestBuilders.put(urlTemplate)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapperToJson(productUpdateRequest)))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(content().json(mapperToJson(errorResponseBuilder(NotFoundException.PRODUCT_NOT_FOUND))));
    }

    @Test
    void deleteProductTest() throws Exception {
        Mockito.doNothing().when(productServiceMock).deleteProduct(Mockito.any());
        this.mockMvc.perform(MockMvcRequestBuilders.delete(urlTemplateWithId))
                .andDo(print())
                .andExpect(status().isNoContent());
    }
}
