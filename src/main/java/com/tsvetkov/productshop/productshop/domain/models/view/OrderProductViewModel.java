package com.tsvetkov.productshop.productshop.domain.models.view;

import java.math.BigDecimal;

public class OrderProductViewModel {
    private ProductDetailsViewModels product;
    private BigDecimal price;


    public OrderProductViewModel() {
    }

    public ProductDetailsViewModels getProduct() {
        return product;
    }

    public void setProduct(ProductDetailsViewModels product) {
        this.product = product;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }
}
