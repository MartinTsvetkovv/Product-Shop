package com.tsvetkov.productshop.productshop.services;

import com.tsvetkov.productshop.productshop.domain.models.service.ProductServiceModel;

import java.util.List;
import java.util.Set;

public interface ProductService {
    ProductServiceModel addProduct(ProductServiceModel productServiceModel);

    List<ProductServiceModel> findAllProducts();

    ProductServiceModel findProductById(String id);

    ProductServiceModel editProduct(String id, ProductServiceModel model);

    Set<ProductServiceModel> findByCategory(String category);

    void deleteProduct(String id);


}
