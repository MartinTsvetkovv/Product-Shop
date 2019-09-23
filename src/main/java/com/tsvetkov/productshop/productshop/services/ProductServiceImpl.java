package com.tsvetkov.productshop.productshop.services;

import com.tsvetkov.productshop.productshop.domain.entities.Category;
import com.tsvetkov.productshop.productshop.domain.entities.Product;
import com.tsvetkov.productshop.productshop.domain.models.service.ProductServiceModel;
import com.tsvetkov.productshop.productshop.repository.ProductRepository;
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

    @Autowired
    public ProductServiceImpl(ProductRepository productRepository, CategoryService categoryService, ModelMapper modelMapper) {
        this.productRepository = productRepository;
        this.categoryService = categoryService;
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
                .map(p -> this.modelMapper.map(p, ProductServiceModel.class))
                .collect(Collectors.toList());
    }

    @Override
    public ProductServiceModel findProductById(String id) {
        Product product = this.productRepository.findById(id).orElseThrow(IllegalArgumentException::new);

        return this.modelMapper.map(product, ProductServiceModel.class);
    }

    @Override
    public ProductServiceModel editProduct(String id, ProductServiceModel model) {
        Product product = this.productRepository.findById(id).orElseThrow(IllegalAccessError::new);

        model.setCategories(this.categoryService.findAllCategories()
                .stream()
                .filter(c -> model.getCategories().contains(c.getId())).collect(Collectors.toList()));


        product.setName(model.getName());
        product.setDescription(model.getDescription());
        product.setPrice(model.getPrice());

        product.setCategories(model.getCategories().stream()
                .map(c -> this.modelMapper.map(c, Category.class)).collect(Collectors.toList()));

        Product savedProduct = this.productRepository.saveAndFlush(product);

        return this.modelMapper.map(savedProduct, ProductServiceModel.class);
    }

    @Override
    public Set<ProductServiceModel> findByCategory(String category) {

//                   return this.productRepository.findByCategory(category)
//                   .stream().map(p -> this.modelMapper.map(p, ProductServiceModel.class))
//                   .collect(Collectors.toSet());

        return this.productRepository.findAll()
                .stream()
                .filter(product -> product.getCategories()
                        .stream()
                        .anyMatch(c -> c.getName().equals(category)))
                .map(p -> this.modelMapper.map(p, ProductServiceModel.class))
                .collect(Collectors.toSet());

    }

    @Override
    public void deleteProduct(String id) {
        this.productRepository.deleteById(id);
    }


}
