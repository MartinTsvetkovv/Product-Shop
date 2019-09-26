package com.tsvetkov.productshop.productshop.validations;

import com.tsvetkov.productshop.productshop.domain.entities.Product;
import com.tsvetkov.productshop.productshop.domain.models.service.CategoryServiceModel;
import com.tsvetkov.productshop.productshop.domain.models.service.ProductServiceModel;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ProductValidationImpl implements ProductValidation {
    @Override
    public boolean isValid(Product product) {
        return product != null;
    }

    @Override
    public boolean isValid(ProductServiceModel productServiceModel) {
        return productServiceModel != null && areCategoriesValid(productServiceModel.getCategories());
    }


    private boolean areCategoriesValid(List<CategoryServiceModel> categories) {
        return categories != null && !categories.isEmpty();
    }

}
