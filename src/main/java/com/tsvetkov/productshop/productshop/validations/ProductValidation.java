package com.tsvetkov.productshop.productshop.validations;

import com.tsvetkov.productshop.productshop.domain.entities.Product;
import com.tsvetkov.productshop.productshop.domain.models.service.ProductServiceModel;

public interface ProductValidation {
    boolean isValid(Product product);

    boolean isValid(ProductServiceModel productServiceModel);
}
