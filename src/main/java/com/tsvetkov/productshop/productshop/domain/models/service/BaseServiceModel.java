package com.tsvetkov.productshop.productshop.domain.models.service;

public class BaseServiceModel {

    private String id;

    protected BaseServiceModel() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
