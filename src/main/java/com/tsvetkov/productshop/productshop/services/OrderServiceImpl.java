package com.tsvetkov.productshop.productshop.services;

import com.tsvetkov.productshop.productshop.domain.entities.Order;
import com.tsvetkov.productshop.productshop.domain.models.service.OrderServiceModel;
import com.tsvetkov.productshop.productshop.repository.OrderRepository;
import com.tsvetkov.productshop.productshop.repository.ProductRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final ModelMapper modelMapper;
    private final UserService userService;
    private final ProductRepository productRepository;

    @Autowired
    public OrderServiceImpl(OrderRepository orderRepository,
                            ModelMapper modelMapper,
                            UserService userService,
                            ProductRepository productRepository) {
        this.orderRepository = orderRepository;
        this.modelMapper = modelMapper;
        this.userService = userService;
        this.productRepository = productRepository;
    }


    @Override
    @Transactional
    public void createOrder(OrderServiceModel orderServiceModel) {
        orderServiceModel.setFinishedOn(LocalDateTime.now());
        Order orderToSave = this.modelMapper.map(orderServiceModel, Order.class);
        this.orderRepository.saveAndFlush(orderToSave);

//        UserServiceModel userByName = this.userService.findUserByName(name);
//        if (!this.userValidation.isValid(userByName)){
//            throw new IllegalArgumentException();
//        }
//        Product product = this.productRepository.findById(productId)
//                .filter(this.productValidation::isValid).orElseThrow(Exception::new);
//
//        Order order = new Order();
//        order.setUser(this.modelMapper.map(userByName, User.class));
//        order.setProduct(product);
//
//        this.orderRepository.save(order);
    }

    @Override
    public List<OrderServiceModel> findAllOrders() {
        return this.orderRepository.findAll()
                .stream()
                .map(o -> this.modelMapper.map(o, OrderServiceModel.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<OrderServiceModel> findOrderByCustomerName(String name) {
        return this.orderRepository.findAllOrdersByCustomer_UsernameOrderByFinishedOn(name)
                .stream()
                .map(o -> this.modelMapper.map(o, OrderServiceModel.class))
                .collect(Collectors.toList());

    }

    @Override
    public OrderServiceModel findOrderById(String id) {

        return this.orderRepository.findById(id)
                .map(o -> this.modelMapper.map(o, OrderServiceModel.class))
                .orElseThrow(() -> new IllegalArgumentException("Nqma Go"));
    }

}
