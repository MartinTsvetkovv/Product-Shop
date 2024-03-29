package com.tsvetkov.productshop.productshop.web.controllers;

import com.tsvetkov.productshop.productshop.domain.models.binding.ProductBindingModel;
import com.tsvetkov.productshop.productshop.domain.models.service.CategoryServiceModel;
import com.tsvetkov.productshop.productshop.domain.models.service.ProductServiceModel;
import com.tsvetkov.productshop.productshop.domain.models.view.ProductAllViewModel;
import com.tsvetkov.productshop.productshop.domain.models.view.ProductDetailsViewModels;
import com.tsvetkov.productshop.productshop.errors.ProductNotFoundException;
import com.tsvetkov.productshop.productshop.services.CategoryService;
import com.tsvetkov.productshop.productshop.services.CloudinaryService;
import com.tsvetkov.productshop.productshop.services.ProductService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/products")
public class ProductController extends BaseController {
    private final ProductService productService;
    private final ModelMapper modelMapper;
    private final CategoryService categoryService;
    private final CloudinaryService cloudinaryService;

    @Autowired
    public ProductController(ProductService productService, ModelMapper modelMapper, CategoryService categoryService, CloudinaryService cloudinaryService) {
        this.productService = productService;
        this.modelMapper = modelMapper;
        this.categoryService = categoryService;
        this.cloudinaryService = cloudinaryService;
    }


    @GetMapping("/add")
    @PreAuthorize("hasRole('ROLE_MODERATOR')")
    public ModelAndView addProduct() {
        return super.view("add-product");
    }

    @PostMapping("/add")
    @PreAuthorize("hasRole('ROLE_MODERATOR')")
    public ModelAndView addProductConfirm(@ModelAttribute ProductBindingModel productBindingModel) throws IOException {
        ProductServiceModel productServiceModel = this.modelMapper.map(productBindingModel, ProductServiceModel.class);
        productServiceModel.setCategories(this.categoryService.findAllCategories()
                .stream()
                .filter(c -> productBindingModel.getCategories().contains(c.getId()))
                .collect(Collectors.toList()));

        productServiceModel.setImageUrl(this.cloudinaryService.uploadImg(productBindingModel.getImageUrl()));

        this.productService.addProduct(productServiceModel);

        return super.redirect("/products/all");
    }

    @GetMapping("/all")
    @PreAuthorize("hasRole('ROLE_MODERATOR')")
    public ModelAndView allProducts(ModelAndView modelAndView) {
        List<ProductAllViewModel> allProucts = this.productService.findAllProducts()
                .stream()
                .map(p -> this.modelMapper.map(p, ProductAllViewModel.class))
                .collect(Collectors.toList());

        modelAndView.addObject("products", allProucts);


        return super.view("all-products", modelAndView);
    }

    @GetMapping("/details/{id}")
    @PreAuthorize("isAuthenticated()")
    public ModelAndView productDetails(@PathVariable String id, ModelAndView modelAndView) throws ProductNotFoundException {
        ProductServiceModel productServiceModel = this.productService.findProductById(id);
        ProductDetailsViewModels productDetails = this.modelMapper.map(productServiceModel, ProductDetailsViewModels.class);

        modelAndView.addObject("product", productDetails);

        return super.view("product-details", modelAndView);

    }

    @GetMapping("/edit/{id}")
    @PreAuthorize("hasRole('ROLE_MODERATOR')")
    public ModelAndView editProduct(@PathVariable String id, ModelAndView modelAndView) throws ProductNotFoundException {
        ProductServiceModel productServiceModel = this.productService.findProductById(id);
        ProductBindingModel productBindingModel = this.modelMapper.map(productServiceModel, ProductBindingModel.class);

        productBindingModel.setCategories(productServiceModel.getCategories()
                .stream()
                .map(CategoryServiceModel::getName).collect(Collectors.toList()));
        modelAndView.addObject("product", productBindingModel);
        return super.view("edit-product", modelAndView);
    }

    @PutMapping("/edit/{id}")
    @PreAuthorize("hasRole('ROLE_MODERATOR')")
    public ModelAndView editProductConfirm(@PathVariable String id, @ModelAttribute ProductBindingModel model) throws ProductNotFoundException {
        //this.productService.editProduct(id, this.modelMapper.map(model, ProductServiceModel.class));
        ProductServiceModel productServiceModel = this.modelMapper.map(model, ProductServiceModel.class);

        productServiceModel.setCategories(this.categoryService
                .findAllCategories()
                .stream()
                .filter(c -> model.getCategories().contains(c.getId()))
                .collect(Collectors.toList()));


        this.productService.editProduct(id, productServiceModel);

        return super.redirect("/products/all");
    }

    @GetMapping("/fetch/{category}")
    @ResponseBody
    public List<ProductAllViewModel> fetchProductByCategory(@PathVariable String category) {
        if (category.equals("all")) {
            return this.productService.findAllProducts()
                    .stream()
                    .map(p -> this.modelMapper.map(p, ProductAllViewModel.class))
                    .collect(Collectors.toList());
        }

        return this.productService.findByCategory(category)
                .stream()
                .map(p -> this.modelMapper.map(p, ProductAllViewModel.class))
                .collect(Collectors.toList());
    }

    @GetMapping("/delete/{id}")
    @PreAuthorize("hasRole('ROLE_MODERATOR')")
    public ModelAndView deleteProduct(@PathVariable String id, ModelAndView modelAndView) {
        ProductServiceModel productServiceModel = null;
        try {
            productServiceModel = this.productService.findProductById(id);
        } catch (ProductNotFoundException e) {
            e.getMessage();
        }
        ProductBindingModel model = this.modelMapper.map(productServiceModel, ProductBindingModel.class);

        model.setCategories(productServiceModel.getCategories()
                .stream()
                .map(CategoryServiceModel::getName)
                .collect(Collectors.toList()));

        modelAndView.addObject("model", model);
        return super.view("delete-product", modelAndView);
    }

    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasRole('ROLE_MODERATOR')")
    public ModelAndView deleteProduct(@PathVariable String id) {
        this.productService.deleteProduct(id);

        return super.redirect("/products/all");
    }

//    @ExceptionHandler(ProductNotFoundException.class)
//    public ModelAndView handleProductException(ProductNotFoundException e) {
//        ModelAndView modelAndView = new ModelAndView("error");
//        modelAndView.addObject("message", e.getMessage());
//        modelAndView.addObject("statusCode", e.getStatusCode());
//        return modelAndView;
//    }


}
