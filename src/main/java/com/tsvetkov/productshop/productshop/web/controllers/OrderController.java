package com.tsvetkov.productshop.productshop.web.controllers;

import com.tsvetkov.productshop.productshop.domain.models.service.ProductServiceModel;
import com.tsvetkov.productshop.productshop.domain.models.view.OrderViewModel;
import com.tsvetkov.productshop.productshop.domain.models.view.ProductDetailsViewModels;
import com.tsvetkov.productshop.productshop.errors.ProductNotFoundException;
import com.tsvetkov.productshop.productshop.services.OrderService;
import com.tsvetkov.productshop.productshop.services.ProductService;
import org.apache.catalina.LifecycleState;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/order")
public class OrderController extends BaseController {

    private final ProductService productService;
    private final ModelMapper modelMapper;
    private final OrderService orderService;

    @Autowired
    public OrderController(ProductService productService, ModelMapper modelMapper, OrderService orderService) {
        this.productService = productService;
        this.modelMapper = modelMapper;
        this.orderService = orderService;
    }

    @GetMapping("/product/{id}")
    @PreAuthorize("isAuthenticated()")
    public ModelAndView productOrder(@PathVariable String id, ModelAndView modelAndView) throws ProductNotFoundException {
        ProductServiceModel productServiceModel = this.productService.findProductById(id);
        ProductDetailsViewModels productView = this.modelMapper.map(productServiceModel, ProductDetailsViewModels.class);

        modelAndView.addObject("product", productView);

        return super.view("orders/order-product", modelAndView);
    }

    @PostMapping("/submit/{id}")
    @PreAuthorize("isAuthenticated()")
    public ModelAndView orderConfirm(@PathVariable String id , Principal principal) throws Exception {
        String name = principal.getName();
        this.orderService.createOrder(id, name);
        return super.redirect("/home");
    }

    @GetMapping("/all")
    @PreAuthorize("hasRole('ROLE_MODERATOR')")
    public ModelAndView findAllOrders(ModelAndView modelAndView){
        List<OrderViewModel> viewModels = this.orderService.findAllOrders()
                .stream()
                .map(o -> this.modelMapper.map(o, OrderViewModel.class))
                .collect(Collectors.toList());

        modelAndView.addObject("orders", viewModels);

        return view("orders/list-orders", modelAndView);
    }

    @GetMapping("/customer")
    @PreAuthorize("isAuthenticated()")
    public ModelAndView findCustomerOrders(ModelAndView modelAndView, Principal principal){
        String name = principal.getName();

        List<OrderViewModel> viewModels = this.orderService.findCustomerByName(name)
                .stream()
                .map(o -> this.modelMapper.map(o, OrderViewModel.class))
                .collect(Collectors.toList());

        modelAndView.addObject("orders", viewModels);

        return view("orders/list-orders", modelAndView);
    }
}
