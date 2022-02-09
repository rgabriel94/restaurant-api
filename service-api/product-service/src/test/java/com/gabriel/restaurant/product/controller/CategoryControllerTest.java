package com.gabriel.restaurant.product.controller;

import com.gabriel.restaurant.product.entity.Category;
import com.gabriel.restaurant.product.exception.EntityActionException;
import com.gabriel.restaurant.product.exception.NotFoundException;
import com.gabriel.restaurant.product.interfaces.CategoryService;
import com.gabriel.restaurant.product.model.dto.category.CategoryCreateRequestDTO;
import com.gabriel.restaurant.product.model.dto.category.CategoryResponseDTO;
import com.gabriel.restaurant.product.model.dto.category.CategoryUpdateRequestDTO;
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
class CategoryControllerTest implements EntityBuilderForTest, MapperForTest {
    private final String urlTemplate = "/categories";

    private Category category;
    private CategoryCreateRequestDTO categoryCreateRequest;
    private CategoryUpdateRequestDTO categoryUpdateRequest;
    private CategoryResponseDTO categoryResponse;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CategoryService categoryServiceMock;

    @BeforeEach
    void beforeEach() {
        category = categoryBuilder(defaultId);
        categoryCreateRequest = categoryCreateRequestBuilder();
        categoryUpdateRequest = categoryUpdateRequestBuilder();
        categoryResponse = categoryResponseBuilder();
    }

    @AfterEach
    void afterEach() {
        category = null;
        categoryCreateRequest = null;
        categoryResponse = null;
    }

    @Test
    void listAllCategoryOrderByNameTest() throws Exception {
        Mockito.when(categoryServiceMock.listAllCategoryOrderByName())
                .thenReturn(List.of(category));
        this.mockMvc.perform(MockMvcRequestBuilders.get(urlTemplate))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(mapperListToJson(List.of(categoryResponse))));
    }

    @Test
    void getCategoryTest() throws Exception {
        Mockito.when(categoryServiceMock.getCategory(Mockito.anyLong()))
                .thenReturn(Optional.of(category));
        this.mockMvc.perform(MockMvcRequestBuilders.get(urlTemplate + "/" + defaultId))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(mapperToJson(categoryResponse)));
    }

    @Test
    void getCategoryNotFoundTest() throws Exception {
        Mockito.when(categoryServiceMock.getCategory(Mockito.anyLong()))
                .thenReturn(Optional.empty());
        this.mockMvc.perform(MockMvcRequestBuilders.get(urlTemplate + "/" + defaultId))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(content().json(mapperToJson(errorResponseBuilder(NotFoundException.CATEGORY_NOT_FOUND))));
    }

    @Test
    void getProductEntityActionExceptionNotIdTest() throws Exception {
        Mockito.when(categoryServiceMock.getCategory(Mockito.anyLong()))
                .thenThrow(new EntityActionException(EntityActionException.CATEGORY_ID_NULL));
        this.mockMvc.perform(MockMvcRequestBuilders.get(urlTemplate + "/" + defaultId))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().json(mapperToJson(errorResponseBuilder(EntityActionException.CATEGORY_ID_NULL))));
    }

    @Test
    void createCategoryTest() throws Exception {
        Mockito.when(categoryServiceMock.createCategory(Mockito.any()))
                .thenReturn(category);
        this.mockMvc.perform(MockMvcRequestBuilders.post(urlTemplate)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapperToJson(categoryCreateRequest)))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(content().json(mapperToJson(categoryResponse)));
    }

    @Test
    void createCategoryResponseExceptionTest() throws Exception {
        Mockito.when(categoryServiceMock.createCategory(Mockito.any()))
                .thenReturn(category);
        this.mockMvc.perform(MockMvcRequestBuilders.post(urlTemplate)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapperToJson(CategoryCreateRequestDTO.builder().build())))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    void updateCategoryTest() throws Exception {
        Mockito.when(categoryServiceMock.updateCategory(Mockito.any()))
                .thenReturn(category);
        this.mockMvc.perform(MockMvcRequestBuilders.put(urlTemplate)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapperToJson(categoryUpdateRequest)))
                .andExpect(status().isOk())
                .andExpect(content().json(mapperToJson(categoryResponse)));
    }

    @Test
    void updateCategoryResponseExceptionTest() throws Exception {
        Mockito.when(categoryServiceMock.updateCategory(Mockito.any()))
                .thenReturn(category);
        this.mockMvc.perform(MockMvcRequestBuilders.put(urlTemplate)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapperToJson(CategoryUpdateRequestDTO.builder().build())))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    void updateCategoryNotFoundTest() throws Exception {
        Mockito.when(categoryServiceMock.updateCategory(Mockito.any()))
                .thenThrow(new NotFoundException(NotFoundException.CATEGORY_NOT_FOUND));
        this.mockMvc.perform(MockMvcRequestBuilders.put(urlTemplate)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapperToJson(categoryUpdateRequest)))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(content().json(mapperToJson(errorResponseBuilder(NotFoundException.CATEGORY_NOT_FOUND))));
    }

    @Test
    void deleteCategoryTest() throws Exception {
        Mockito.doNothing().when(categoryServiceMock)
                .deleteCategory(Mockito.anyLong());
        this.mockMvc.perform(MockMvcRequestBuilders.delete(urlTemplate + "/" + defaultId))
                .andDo(print())
                .andExpect(status().isNoContent());
    }
}