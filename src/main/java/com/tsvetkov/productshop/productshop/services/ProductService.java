package com.tsvetkov.productshop.productshop.services;

import com.tsvetkov.productshop.productshop.domain.models.service.ProductServiceModel;

import java.util.List;

public interface ProductService {
    ProductServiceModel addProduct(ProductServiceModel productServiceModel);

    List<ProductServiceModel> findAllProducts();

    ProductServiceModel findProductById(String id);


}
