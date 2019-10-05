package com.tsvetkov.productshop.productshop.web.controllers;

import com.tsvetkov.productshop.productshop.domain.models.binding.CategoryBindingModel;
import com.tsvetkov.productshop.productshop.domain.models.binding.CategoryEditBindingModel;
import com.tsvetkov.productshop.productshop.domain.models.service.CategoryServiceModel;
import com.tsvetkov.productshop.productshop.domain.models.view.CategoriesAllViewModel;
import com.tsvetkov.productshop.productshop.domain.models.view.CategoryViewModel;
import com.tsvetkov.productshop.productshop.errors.CategoryNotFoundException;
import com.tsvetkov.productshop.productshop.services.CategoryService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/categories")
public class CategoryController extends BaseController {

    private final CategoryService categoryService;
    private final ModelMapper modelMapper;

    @Autowired
    public CategoryController(CategoryService categoryService, ModelMapper modelMapper) {
        this.categoryService = categoryService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/add")
    @PreAuthorize("hasRole('ROLE_MODERATOR')")
    public ModelAndView addCategory() {
        return super.view("add-category");
    }

    @PostMapping("/add")
    @PreAuthorize("hasRole('ROLE_MODERATOR')")
    public ModelAndView addCategoryConfirmed(@ModelAttribute CategoryBindingModel categoryBindingModel) {
        this.categoryService.addCategory(this.modelMapper.map(categoryBindingModel, CategoryServiceModel.class));

        return super.view("add-category");
    }

    @GetMapping("/all")
    @PreAuthorize("hasRole('ROLE_MODERATOR')")
    public ModelAndView allCategories(ModelAndView modelAndView) {
        List<CategoriesAllViewModel> allCategories = this.categoryService.findAllCategories().stream()
                .map(c -> this.modelMapper.map(c, CategoriesAllViewModel.class))
                .collect(Collectors.toList());

        modelAndView.addObject("categories", allCategories);

        return super.view("all-categories", modelAndView);
    }

    @GetMapping("/edit/{id}")
    @PreAuthorize("hasRole('ROLE_MODERATOR')")
    public ModelAndView editUser(@PathVariable String id, ModelAndView modelAndView) throws CategoryNotFoundException {
        CategoryServiceModel categoryById = this.categoryService.findCategoryById(id);
        CategoryViewModel categoryViewModel = this.modelMapper.map(categoryById, CategoryViewModel.class);

        modelAndView.addObject("model", categoryViewModel);

        return super.view("edit-category", modelAndView);

    }

    @PutMapping("/edit/{id}")
    @PreAuthorize("hasRole('ROLE_MODERATOR')")
    public ModelAndView editConfirmed(@PathVariable String id, @ModelAttribute CategoryEditBindingModel model) throws CategoryNotFoundException {

        this.categoryService.editCategory(id, this.modelMapper.map(model, CategoryServiceModel.class));

        return super.redirect("/categories/all");

    }

    @GetMapping("/delete/{id}")
    @PreAuthorize("hasRole('ROLE_MODERATOR')")
    public ModelAndView deleteById(@PathVariable String id, ModelAndView modelAndView) throws CategoryNotFoundException {
        CategoryServiceModel categoryById = this.categoryService.findCategoryById(id);
        CategoryViewModel viewModel = this.modelMapper.map(categoryById, CategoryViewModel.class);
        modelAndView.addObject("model", viewModel);

        return super.view("delete-category", modelAndView);
    }

    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasRole('ROLE_MODERATOR')")
    public ModelAndView deleteCategory(@PathVariable String id) {
        this.categoryService.deleteCategory(id);

        return super.redirect("/categories/all");
    }

    @GetMapping("/fetch")
    @PreAuthorize("hasRole('ROLE_MODERATOR')")
    @ResponseBody
    public List<CategoryViewModel> fetchCategories(){
        return this.categoryService.findAllCategories().stream()
                .map(c -> this.modelMapper.map(c, CategoryViewModel.class))
                .collect(Collectors.toList());
    }
    @ExceptionHandler(CategoryNotFoundException.class)
    public ModelAndView handleProductException(CategoryNotFoundException e) {
        ModelAndView modelAndView = new ModelAndView("error");
        modelAndView.addObject("message", e.getMessage());
        modelAndView.addObject("statusCode", e.getStatusCode());
        return modelAndView;
    }

}
