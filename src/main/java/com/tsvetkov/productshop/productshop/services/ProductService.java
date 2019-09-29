package com.tsvetkov.productshop.productshop.services;

import com.tsvetkov.productshop.productshop.domain.models.service.ProductServiceModel;
import com.tsvetkov.productshop.productshop.errors.ProductNotFoundException;

import java.util.List;
import java.util.Set;

public interface ProductService {
    ProductServiceModel addProduct(ProductServiceModel productServiceModel);

    List<ProductServiceModel> findAllProducts();

    ProductServiceModel findProductById(String id) throws ProductNotFoundException;

    ProductServiceModel editProduct(String id, ProductServiceModel model) throws ProductNotFoundException;

    List<ProductServiceModel> findByCategory(String category);

    void deleteProduct(String id);


}
