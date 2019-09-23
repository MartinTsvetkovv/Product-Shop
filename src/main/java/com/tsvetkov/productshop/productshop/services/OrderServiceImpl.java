package com.tsvetkov.productshop.productshop.services;

import com.tsvetkov.productshop.productshop.domain.entities.Order;
import com.tsvetkov.productshop.productshop.domain.entities.Product;
import com.tsvetkov.productshop.productshop.domain.entities.User;
import com.tsvetkov.productshop.productshop.domain.models.service.OrderServiceModel;
import com.tsvetkov.productshop.productshop.domain.models.service.UserServiceModel;
import com.tsvetkov.productshop.productshop.repository.OrderRepository;
import com.tsvetkov.productshop.productshop.repository.ProductRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final ModelMapper modelMapper;
    private final UserService userService;
    private final ProductRepository productRepository;

    public OrderServiceImpl(OrderRepository orderRepository, ModelMapper modelMapper, UserService userService, ProductRepository productRepository) {
        this.orderRepository = orderRepository;
        this.modelMapper = modelMapper;
        this.userService = userService;
        this.productRepository = productRepository;
    }


    @Override
    public void createOrder(String productId, String name) {
        UserServiceModel userByName = this.userService.findUserByName(name);

        Product product = this.productRepository.findById(productId).orElseThrow();

        Order order = new Order();
        order.setUser(this.modelMapper.map(userByName, User.class));
        order.setProduct(product);

        this.orderRepository.save(order);
    }

    @Override
    public List<OrderServiceModel> findAllOrders() {
        return this.orderRepository.findAll()
                .stream()
                .map(o -> this.modelMapper.map(o, OrderServiceModel.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<OrderServiceModel> findCustomerByName(String name) {
        return this.orderRepository.findAllByUser_Username(name)
                .stream()
                .map(o -> this.modelMapper.map(o, OrderServiceModel.class))
                .collect(Collectors.toList());
    }
}
