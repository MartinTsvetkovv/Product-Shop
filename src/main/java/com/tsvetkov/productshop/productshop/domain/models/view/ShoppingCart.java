package com.tsvetkov.productshop.productshop.domain.models.view;

import java.io.Serializable;

public class ShoppingCart implements Serializable {

    private ProductDetailsViewModels product;
    private int quantity;


    public ShoppingCart() {
    }

    public ProductDetailsViewModels getProduct() {
        return product;
    }

    public void setProduct(ProductDetailsViewModels product) {
        this.product = product;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
