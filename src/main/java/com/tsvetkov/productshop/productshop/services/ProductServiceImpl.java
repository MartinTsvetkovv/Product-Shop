package com.tsvetkov.productshop.productshop.services;

import com.tsvetkov.productshop.productshop.domain.entities.Product;
import com.tsvetkov.productshop.productshop.domain.models.service.ProductServiceModel;
import com.tsvetkov.productshop.productshop.repository.ProductRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public ProductServiceImpl(ProductRepository productRepository, ModelMapper modelMapper) {
        this.productRepository = productRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public ProductServiceModel addProduct(ProductServiceModel productServiceModel) {
        Product product = this.modelMapper.map(productServiceModel, Product.class);
        Product savedProduct = this.productRepository.saveAndFlush(product);
        return this.modelMapper.map(savedProduct, ProductServiceModel.class);
    }

    @Override
    public List<ProductServiceModel> findAllProducts() {
        return this.productRepository.findAll()
                .stream()
                .map(p -> this.modelMapper.map(p , ProductServiceModel.class))
                .collect(Collectors.toList());
    }

    @Override
    public ProductServiceModel findProductById(String id) {
        Product product = this.productRepository.findById(id).orElseThrow(IllegalArgumentException::new);

        return this.modelMapper.map(product, ProductServiceModel.class);
    }
}
