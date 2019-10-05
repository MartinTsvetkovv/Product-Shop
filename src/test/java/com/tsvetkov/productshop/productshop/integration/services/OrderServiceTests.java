//package com.tsvetkov.productshop.productshop.integration.services;
//
//import com.sun.jdi.event.ExceptionEvent;
//import com.tsvetkov.productshop.productshop.domain.entities.Order;
//import com.tsvetkov.productshop.productshop.domain.entities.Product;
//import com.tsvetkov.productshop.productshop.domain.entities.User;
//import com.tsvetkov.productshop.productshop.domain.models.service.OrderServiceModel;
//import com.tsvetkov.productshop.productshop.domain.models.service.UserServiceModel;
//import com.tsvetkov.productshop.productshop.repository.OrderRepository;
//import com.tsvetkov.productshop.productshop.repository.ProductRepository;
//import com.tsvetkov.productshop.productshop.services.OrderService;
//import com.tsvetkov.productshop.productshop.services.UserService;
//import com.tsvetkov.productshop.productshop.validations.ProductValidation;
//import com.tsvetkov.productshop.productshop.validations.UserValidation;
//import org.junit.Before;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.test.context.junit4.SpringRunner;
//
//import java.math.BigDecimal;
//import java.util.ArrayList;
//import java.util.List;
//
//import static org.junit.Assert.assertEquals;
//import static org.junit.Assert.assertTrue;
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.Mockito.verify;
//import static org.mockito.Mockito.when;
//
//@SpringBootTest
//@RunWith(SpringRunner.class)
//public class OrderServiceTests {
//
//    @Autowired
//    OrderService orderService;
//    @MockBean
//    OrderRepository orderRepository;
//    @MockBean
//    UserValidation userValidation;
//    @MockBean
//    ProductValidation productValidation;
//    @MockBean
//    UserService userService;
//    @MockBean
//    ProductRepository productRepository;
//
//    private List<Order> orders;
//
//    @Before
//    public void setUpTest() {
//        orders = new ArrayList<>();
//        when(this.orderRepository.findAll())
//                .thenReturn(orders);
//    }
//
//    @Test
//    public void findAllOrders_when1Order_return1Order() {
//        String customer = "Test Customer";
//        String imageUrl = "http://imageurl";
//        BigDecimal price = new BigDecimal(1.34);
//        String productName = "Product test";
//
//        Order order = new Order();
//        order.setUser(new User() {{
//            setUsername(customer);
//        }});
//        order.setProduct(new Product() {{
//            setPrice(price);
//            setName(productName);
//            setImageUrl(imageUrl);
//        }});
//
//        orders.add(order);
//
//        List<OrderServiceModel> result = this.orderService.findAllOrders();
//        OrderServiceModel orderServiceModel = result.get(0);
//
//        assertEquals(1, result.size());
//        assertEquals(customer, orderServiceModel.getCustomer());
//        assertEquals(productName, orderServiceModel.getName());
//        assertEquals(imageUrl, orderServiceModel.getImageUrl());
//    }
//
//    @Test
//    public void findAllOrders_when1Order_returnException() {
//        orders.clear();
//        List<OrderServiceModel> result = this.orderService.findAllOrders();
//
//        assertTrue(result.isEmpty());
//    }
//
//    @Test
//    public void createOrder_whenUserAndProductAreValid_orderCreated() throws Exception {
//
//        when(userValidation.isValid(any()))
//                .thenReturn(true);
//
//        when(productValidation.isValid(any(Product.class)))
//                .thenReturn(true);
//
//        when(userService.findUserByName(any()))
//                .thenReturn(new UserServiceModel());
//        when(productRepository.findById(any()))
//                .thenReturn(java.util.Optional.of(new Product()));
//
//        orderService.createOrder("","");
//
//        verify(orderRepository).save(any());
//
//    }
//    @Test(expected = Exception.class)
//    public void createOrder_whenUserIsValidAndProductIsNotValid_orderCreated() throws Exception {
//        when(userValidation.isValid(any()))
//                .thenReturn(true);
//        when(productValidation.isValid(any(Product.class)))
//                .thenReturn(false);
//
//        orderService.createOrder("", "");
//    }
//
//    @Test(expected = Exception.class)
//    public void createOrder_whenUserIsNotValidAndProductIsValid_orderCreated() throws Exception {
//        when(userValidation.isValid(any()))
//                .thenReturn(false);
//        when(productValidation.isValid(any(Product.class)))
//                .thenReturn(true);
//
//        orderService.createOrder("", "");
//    }
//
//    @Test(expected = Exception.class)
//    public void createOrder_whenUserIsNotValidAndProductIsNotValid_orderCreated() throws Exception {
//        when(userValidation.isValid(any()))
//                .thenReturn(false);
//        when(productValidation.isValid(any(Product.class)))
//                .thenReturn(false);
//
//        orderService.createOrder("", "");
//    }
//
//
//}
