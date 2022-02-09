package com.gabriel.restaurant.product.controller;

import com.gabriel.restaurant.product.entity.Product;
import com.gabriel.restaurant.product.exception.NotFoundException;
import com.gabriel.restaurant.product.interfaces.ProductService;
import com.gabriel.restaurant.product.model.dto.product.ProductCreateRequestDTO;
import com.gabriel.restaurant.product.model.dto.product.ProductResponseDTO;
import com.gabriel.restaurant.product.model.dto.product.ProductUpdateRequestDTO;
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
@RequestMapping(value = "products")
public class ProductController extends BaseController {

    @Autowired
    ProductService productService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<ProductResponseDTO> listAllProducts() {
        List<Product> products = productService.listAllProductsOrderByCategoryPriorityAsc();
        return mapper.convert(products, ProductResponseDTO.class);

    }

    @GetMapping(value = "/{productId}")
    @ResponseStatus(HttpStatus.OK)
    public ProductResponseDTO getProduct(@PathVariable Long productId) {
        Product product = productService.getProduct(productId).orElseThrow(() -> new NotFoundException(NotFoundException.PRODUCT_NOT_FOUND));
        return mapper.convert(product, ProductResponseDTO.class);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ProductResponseDTO createProduct(@Valid @RequestBody ProductCreateRequestDTO productRequestDTO, BindingResult bindingResult) {
        bindingResultService.bindingResultErrors(bindingResult);
        Product product = mapper.convert(productRequestDTO, Product.class);
        return mapper.convert(productService.createProduct(product), ProductResponseDTO.class);
    }

    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public ProductResponseDTO updateProduct(@Valid @RequestBody ProductUpdateRequestDTO productRequestDTO, BindingResult bindingResult) {
        bindingResultService.bindingResultErrors(bindingResult);
        Product product = mapper.convert(productRequestDTO, Product.class);
        return mapper.convert(productService.updateProduct(product), ProductResponseDTO.class);
    }

    @DeleteMapping(value = "/{productId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteProduct(@PathVariable Long productId) {
        productService.deleteProduct(productId);
    }
}
