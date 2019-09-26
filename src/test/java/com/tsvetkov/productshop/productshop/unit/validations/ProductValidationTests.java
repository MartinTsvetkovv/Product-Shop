package com.tsvetkov.productshop.productshop.unit.validations;

import com.tsvetkov.productshop.productshop.domain.entities.Category;
import com.tsvetkov.productshop.productshop.domain.entities.Product;
import com.tsvetkov.productshop.productshop.domain.models.service.CategoryServiceModel;
import com.tsvetkov.productshop.productshop.domain.models.service.ProductServiceModel;
import com.tsvetkov.productshop.productshop.services.ProductService;
import com.tsvetkov.productshop.productshop.validations.ProductValidation;
import com.tsvetkov.productshop.productshop.validations.ProductValidationImpl;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static junit.framework.TestCase.assertFalse;
import static junit.framework.TestCase.assertTrue;

public class ProductValidationTests {
    private ProductValidation productValidation;

    @Before
    public void setUp(){
        this.productValidation = new ProductValidationImpl();
    }
    @Test
    public void productValidationIsValid(){
        Product product = new Product();
        product.setCategories(List.of(new Category()));
        boolean isValid = this.productValidation.isValid(product);
        assertTrue(isValid);

    }
    @Test
    public void productValidationIsNOTValid(){
        Product product = null;
        boolean isValid = this.productValidation.isValid(product);
        assertFalse(isValid);
    }

    @Test
    public void isProductServiceModelValidationValid_returnTrue(){
        ProductServiceModel product = new ProductServiceModel();
        product.setCategories(List.of(new CategoryServiceModel()));
        boolean isValid = this.productValidation.isValid(product);
        assertTrue(isValid);

    }


}
