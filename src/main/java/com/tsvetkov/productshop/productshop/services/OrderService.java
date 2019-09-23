package com.tsvetkov.productshop.productshop.services;

import com.tsvetkov.productshop.productshop.domain.models.service.OrderServiceModel;

import java.util.List;

public interface OrderService {

    void createOrder(String productId, String name);


    List<OrderServiceModel> findAllOrders();

    List<OrderServiceModel> findCustomerByName(String name);
}
