package com.tsvetkov.productshop.productshop.domain.models.service;

import java.math.BigDecimal;

public class OfferServiceModel {

    private ProductServiceModel product;
    private BigDecimal price;

    public OfferServiceModel() {
    }

    public ProductServiceModel getProduct() {
        return product;
    }

    public void setProduct(ProductServiceModel product) {
        this.product = product;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }
}
