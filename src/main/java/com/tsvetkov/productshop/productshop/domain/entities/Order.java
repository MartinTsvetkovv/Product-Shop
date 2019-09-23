package com.tsvetkov.productshop.productshop.domain.entities;

import com.fasterxml.jackson.databind.ser.Serializers;

import javax.persistence.*;

@Entity
@Table(name = "orders")
public class Order extends BaseEntity {
    @ManyToOne(targetEntity = User.class)
    private User user;
    @ManyToOne(targetEntity = Product.class)
    private Product product;

    public Order() {
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }
}
