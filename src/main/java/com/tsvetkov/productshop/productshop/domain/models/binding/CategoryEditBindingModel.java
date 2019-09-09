package com.tsvetkov.productshop.productshop.domain.models.binding;

public class CategoryEditBindingModel {

    private String id;
    private String name;

    public CategoryEditBindingModel() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
