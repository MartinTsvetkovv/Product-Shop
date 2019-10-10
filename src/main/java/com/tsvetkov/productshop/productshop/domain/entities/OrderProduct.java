package com.tsvetkov.productshop.productshop.domain.entities;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "order_products")
public class OrderProduct extends BaseEntity {

    @ManyToOne(targetEntity = Product.class)
    @JoinColumn(
            name = "product_id",
            referencedColumnName = "id"
    )
    private Product product;
    private BigDecimal price;

    public OrderProduct() {
    }


    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    @Column(name = "price")
    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }
}
