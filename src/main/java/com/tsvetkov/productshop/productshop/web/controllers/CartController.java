package com.tsvetkov.productshop.productshop.web.controllers;

import com.tsvetkov.productshop.productshop.domain.models.service.OrderServiceModel;
import com.tsvetkov.productshop.productshop.domain.models.service.ProductServiceModel;
import com.tsvetkov.productshop.productshop.domain.models.view.ProductDetailsViewModels;
import com.tsvetkov.productshop.productshop.domain.models.view.ShoppingCart;
import com.tsvetkov.productshop.productshop.errors.ProductNotFoundException;
import com.tsvetkov.productshop.productshop.services.OrderService;
import com.tsvetkov.productshop.productshop.services.ProductService;
import com.tsvetkov.productshop.productshop.services.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.security.Principal;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

@Controller
@RequestMapping("/cart")
public class CartController extends BaseController {
    private final ProductService productService;
    private final UserService userService;
    private final OrderService orderService;
    private final ModelMapper modelMapper;

    @Autowired
    public CartController(ProductService productService, UserService userService, OrderService orderService, ModelMapper modelMapper) {
        this.productService = productService;
        this.userService = userService;
        this.orderService = orderService;
        this.modelMapper = modelMapper;
    }


    @PostMapping("/add-product")
    @PreAuthorize("isAuthenticated()")
    public ModelAndView addToCartConfirm(String id, int quantity, HttpSession session) throws ProductNotFoundException {
        //this.initCart(session);

        ProductDetailsViewModels product = this.modelMapper
                .map(this.productService.findProductById(id), ProductDetailsViewModels.class);


        ShoppingCart cartItem = new ShoppingCart();
        cartItem.setProduct(product);
        cartItem.setQuantity(quantity);

        List<ShoppingCart> shoppingCarts = this.retrieveCart(session);
        this.addItem(cartItem, shoppingCarts);

        return redirect("/home");
    }

    @GetMapping("/details")
    @PreAuthorize("isAuthenticated()")
    public ModelAndView cartDetails(ModelAndView modelAndView, HttpSession session) {
        List<ShoppingCart> shoppingCarts = this.retrieveCart(session);

        modelAndView.addObject("totalPrice", this.calculateTotalPrice(shoppingCarts));
        return view("cart/cart-details", modelAndView);

    }

    @DeleteMapping("/remove-product")
    @PreAuthorize("isAuthenticated()")
    public ModelAndView removeProductConfirm(String id, HttpSession session) {
        List<ShoppingCart> shoppingCarts = this.retrieveCart(session);

        this.removeProductFromCart(id, shoppingCarts);

        return redirect("/cart/details");
    }

    @PostMapping("/checkout")
    @PreAuthorize("isAuthenticated()")
    public ModelAndView checkoutConfirm(HttpSession session, Principal principal) throws Exception {
        List<ShoppingCart> shoppingCartItems = this.retrieveCart(session);

        OrderServiceModel orderServiceModel = this.prepareOrder(shoppingCartItems, principal.getName());
        this.orderService.createOrder(orderServiceModel);
        return redirect("/home");
    }

    private OrderServiceModel prepareOrder(List<ShoppingCart> shoppingCartItems, String name) {
        OrderServiceModel orderServiceModel = new OrderServiceModel();
        orderServiceModel.setCustomer(this.userService.findUserByName(name));
        List<ProductServiceModel> products = new ArrayList<>();

        for (ShoppingCart shoppingCartItem : shoppingCartItems) {
            ProductServiceModel productServiceModel = this.modelMapper.map(shoppingCartItem, ProductServiceModel.class);

            for (int i = 0; i < shoppingCartItem.getQuantity(); i++) {
                products.add(productServiceModel);
            }

        }
        orderServiceModel.setProducts(products);
        orderServiceModel.setTotalPrice(this.calculateTotalPrice(shoppingCartItems));

        return orderServiceModel;
    }

    private void removeProductFromCart(String id, List<ShoppingCart> shoppingCarts) {
        shoppingCarts.removeIf(item -> item.getProduct().getId().equals(id));
    }


    private List<ShoppingCart> retrieveCart(HttpSession session) {
        this.initCart(session);

        return ((List<ShoppingCart>) session.getAttribute("shopping-cart"));
    }

    private void initCart(HttpSession session) {
        if (session.getAttribute("shopping-cart") == null) {
            session.setAttribute("shopping-cart", new LinkedList<>());
        }
    }

    private BigDecimal calculateTotalPrice(List<ShoppingCart> cart) {
        BigDecimal result = new BigDecimal(0);

        for (ShoppingCart item : cart) {
            result = result.add(item.getProduct().getPrice()).multiply(new BigDecimal(item.getQuantity()));
        }

        return result;
    }

    private void addItem(ShoppingCart item, List<ShoppingCart> cart) {
        String itemId = item.getProduct().getId();
        for (ShoppingCart shoppingCart : cart) {
            if (shoppingCart.getProduct().getId().equals(itemId)) {
                shoppingCart.setQuantity(shoppingCart.getQuantity() + item.getQuantity());
                return;
            }
        }
        cart.add(item);

    }

}
