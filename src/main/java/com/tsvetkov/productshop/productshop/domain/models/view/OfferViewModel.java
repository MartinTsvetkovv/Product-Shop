package com.tsvetkov.productshop.productshop.domain.models.view;

import java.math.BigDecimal;

public class OfferViewModel {
    private ProductDetailsViewModels product;
    private BigDecimal price;

    public OfferViewModel() {
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


