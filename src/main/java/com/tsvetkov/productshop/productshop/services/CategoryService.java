package com.tsvetkov.productshop.productshop.services;

import com.tsvetkov.productshop.productshop.domain.models.service.CategoryServiceModel;
import com.tsvetkov.productshop.productshop.errors.CategoryNotFoundException;

import java.util.List;

public interface CategoryService {

    void addCategory(CategoryServiceModel categoryServiceModel);

    List<CategoryServiceModel> findAllCategories();

    CategoryServiceModel findCategoryById(String id) throws CategoryNotFoundException;

    CategoryServiceModel editCategory(String id, CategoryServiceModel categoryServiceModel) throws CategoryNotFoundException;

    void deleteCategory(String id);

}
