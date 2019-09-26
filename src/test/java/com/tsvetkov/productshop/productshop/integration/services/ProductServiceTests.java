package com.tsvetkov.productshop.productshop.integration.services;

import com.tsvetkov.productshop.productshop.domain.entities.Product;
import com.tsvetkov.productshop.productshop.domain.models.service.CategoryServiceModel;
import com.tsvetkov.productshop.productshop.domain.models.service.ProductServiceModel;
import com.tsvetkov.productshop.productshop.repository.ProductRepository;
import com.tsvetkov.productshop.productshop.services.ProductService;
import com.tsvetkov.productshop.productshop.validations.ProductValidation;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
@RunWith(SpringRunner.class)
public class ProductServiceTests {

    @Autowired
    ProductService productService;
    @MockBean
    ProductRepository productRepository;
    @MockBean
    ProductValidation productValidation;

    @Test
    public void createProduct_whenValidProductIsCreated() {

        ProductServiceModel product = new ProductServiceModel();
        product.setCategories(List.of(new CategoryServiceModel()));
        
        when(productValidation.isValid(product))
                .thenReturn(true);

        when(this.productRepository.save(any(Product.class)))
                .thenReturn(new Product());

        this.productService.addProduct(product);

        verify(this.productRepository).save(any());

    }


}
