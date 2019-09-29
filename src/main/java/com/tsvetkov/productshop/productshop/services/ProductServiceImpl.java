package com.tsvetkov.productshop.productshop.services;

import com.tsvetkov.productshop.productshop.domain.entities.Category;
import com.tsvetkov.productshop.productshop.domain.entities.Product;
import com.tsvetkov.productshop.productshop.domain.models.service.ProductServiceModel;
import com.tsvetkov.productshop.productshop.errors.ProductNotFoundException;
import com.tsvetkov.productshop.productshop.repository.ProductRepository;
import com.tsvetkov.productshop.productshop.validations.ProductValidation;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final CategoryService categoryService;
    private final ModelMapper modelMapper;
    private final ProductValidation productValidation;

    @Autowired
    public ProductServiceImpl(ProductRepository productRepository,
                              CategoryService categoryService,
                              ModelMapper modelMapper,
                              ProductValidation productValidation) {
        this.productRepository = productRepository;
        this.categoryService = categoryService;
        this.modelMapper = modelMapper;
        this.productValidation = productValidation;
    }

    @Override
    public ProductServiceModel addProduct(ProductServiceModel productServiceModel) {
        if (!this.productValidation.isValid(productServiceModel)) {
            throw new IllegalArgumentException();
        }
        Product product = this.modelMapper.map(productServiceModel, Product.class);
        Product savedProduct = this.productRepository.save(product);
        return this.modelMapper.map(savedProduct, ProductServiceModel.class);
    }

    @Override
    public List<ProductServiceModel> findAllProducts() {
        return this.productRepository.findAll()
                .stream()
                .map(p -> this.modelMapper.map(p, ProductServiceModel.class))
                .collect(Collectors.toList());
    }

    @Override
    public ProductServiceModel findProductById(String id) throws ProductNotFoundException {
        Product product = this.productRepository
                .findById(id)
                .orElseThrow(() -> new ProductNotFoundException("Product with the given id was not found"));

        return this.modelMapper.map(product, ProductServiceModel.class);
    }

    @Override
    public ProductServiceModel editProduct(String id, ProductServiceModel productServiceModel) throws ProductNotFoundException {
        Product product = this.productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException("Product with the given id was not found"));


        product.setName(productServiceModel.getName());
        product.setDescription(productServiceModel.getDescription());
        product.setPrice(productServiceModel.getPrice());

        product.setCategories(productServiceModel.getCategories().stream()
                .map(c -> this.modelMapper.map(c, Category.class))
                .collect(Collectors.toList()));

        Product savedProduct = this.productRepository.saveAndFlush(product);

        return this.modelMapper.map(savedProduct, ProductServiceModel.class);
    }

    @Override
    public List<ProductServiceModel> findByCategory(String category) {

//                   return this.productRepository.findByCategory(category)
//                   .stream().map(p -> this.modelMapper.map(p, ProductServiceModel.class))
//                   .collect(Collectors.toSet());

        return this.productRepository.findAll()
                .stream()
                .filter(product -> product.getCategories()
                        .stream()
                        .anyMatch(c -> c.getName().equals(category)))
                .map(p -> this.modelMapper.map(p, ProductServiceModel.class))
                .collect(Collectors.toList());

    }

    @Override
    public void deleteProduct(String id) {
        this.productRepository.deleteById(id);
    }


}
