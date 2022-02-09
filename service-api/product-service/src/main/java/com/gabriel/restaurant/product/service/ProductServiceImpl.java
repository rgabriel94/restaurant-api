package com.gabriel.restaurant.product.service;

import com.gabriel.restaurant.product.entity.Product;
import com.gabriel.restaurant.product.exception.EntityActionException;
import com.gabriel.restaurant.product.exception.NotFoundException;
import com.gabriel.restaurant.product.interfaces.ProductService;
import com.gabriel.restaurant.product.repository.ProductRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Log4j2
@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    ProductRepository productRepository;

    @Override
    public List<Product> listAllProductsOrderByCategoryPriorityAsc() {
        return productRepository.findAllByOrderByCategoryPriorityAsc();
    }

    @Override
    public Optional<Product> getProduct(Long productId) throws EntityActionException {
        return productRepository.findById(Optional.ofNullable(productId).orElseThrow(
                () -> new EntityActionException(EntityActionException.PRODUCT_ID_NULL)
        ));
    }

    @Override
    public Product createProduct(Product product) throws EntityActionException {
        return persist(product);
    }

    @Override
    public Product updateProduct(Product product) throws NotFoundException, EntityActionException {
        if (getProduct(product.getId()).isEmpty())
                throw new NotFoundException(NotFoundException.PRODUCT_NOT_FOUND);
        return persist(product);
    }

    @Override
    public void deleteProduct(Long productId) throws EntityActionException {
        productRepository.deleteById(Optional.ofNullable(productId).orElseThrow(
                () -> new EntityActionException(EntityActionException.PRODUCT_ID_NULL)
        ));
    }

    Product persist(Product product) throws EntityActionException {
        try {
            return productRepository.saveAndFlush(product);
        } catch (Exception e) {
            logErrorWrite(e);
            throw new EntityActionException(EntityActionException.PRODUCT_NOT_SAVE);
        }
    }

    void logErrorWrite(Exception e) {
        log.error(e);
    }
}
