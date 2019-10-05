package com.tsvetkov.productshop.productshop.domain.models.service;

import com.tsvetkov.productshop.productshop.domain.entities.Product;
import com.tsvetkov.productshop.productshop.domain.entities.User;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public class OrderServiceModel extends BaseServiceModel {
    private List<ProductServiceModel> products;
    private UserServiceModel customer;
    private BigDecimal totalPrice;
    private LocalDate finishedOn;

    public OrderServiceModel() {
    }

    public List<ProductServiceModel> getProducts() {
        return products;
    }

    public void setProducts(List<ProductServiceModel> products) {
        this.products = products;
    }

    public UserServiceModel getCustomer() {
        return customer;
    }

    public void setCustomer(UserServiceModel customer) {
        this.customer = customer;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    public LocalDate getFinishedOn() {
        return finishedOn;
    }

    public void setFinishedOn(LocalDate finishedOn) {
        this.finishedOn = finishedOn;
    }

//    @Override
//    public void configureMappings(ModelMapper mapper) {
//        mapper.createTypeMap(Order.class, OrderServiceModel.class)
//                .addMapping(entity -> entity.getProduct().getName(),
//                        (dto, value) -> dto.setName((String) value))
//
//                .addMapping(entity -> entity.getProduct().getPrice(),
//                        (dto, value) -> dto.setPrice((BigDecimal) value))
//
//                .addMapping(entity -> entity.getProduct().getImageUrl(),
//                        (dto, value) -> dto.setImageUrl((String) value))
//
//                .addMapping(entity -> entity.getUser().getUsername(),
//                        (dto, value) -> dto.setCustomer((String) value));
//    }
}
