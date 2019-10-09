package com.tsvetkov.productshop.productshop.services;

import com.tsvetkov.productshop.productshop.domain.models.service.OrderServiceModel;
import javassist.NotFoundException;

import java.util.List;

public interface OrderService {

    void createOrder(OrderServiceModel orderServiceModel) throws Exception;

    List<OrderServiceModel> findAllOrders();

    List<OrderServiceModel> findOrderByCustomerName(String name);

    OrderServiceModel findOrderById(String id) throws NotFoundException;
}
