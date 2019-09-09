package com.tsvetkov.productshop.productshop.web.controllers;

import com.tsvetkov.productshop.productshop.domain.models.binding.UserBindingModel;
import com.tsvetkov.productshop.productshop.domain.models.binding.UserEditBindingModel;
import com.tsvetkov.productshop.productshop.domain.models.service.UserServiceModel;
import com.tsvetkov.productshop.productshop.domain.models.view.UserViewModel;
import com.tsvetkov.productshop.productshop.domain.models.view.UsersAllViewModel;
import com.tsvetkov.productshop.productshop.services.UserService;
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
@RequestMapping("/users")
public class UserController extends BaseController {
    private final UserService userService;
    private final ModelMapper modelMapper;

    @Autowired
    public UserController(UserService userService, ModelMapper modelMapper) {
        this.userService = userService;
        this.modelMapper = modelMapper;
    }


    @GetMapping("/register")
    @PreAuthorize("isAnonymous()")
    public ModelAndView register(){
        return super.view("register");
    }

    @PostMapping("/register")
    @PreAuthorize("isAnonymous()")
    public ModelAndView registerConfirm(@ModelAttribute UserBindingModel model){
        if(!model.getPassword().equals(model.getConfirmPassword())){
            return super.view("register");
        }

        this.userService.registerUser(this.modelMapper.map(model, UserServiceModel.class));
        return super.redirect("/users/login");
    }

    @GetMapping("/login")
    @PreAuthorize("isAnonymous()")
    public ModelAndView login(){
        return super.view("login");
    }

    @GetMapping("/profile")
    @PreAuthorize("isAuthenticated()")
    public ModelAndView profile(Principal principal, ModelAndView modelAndView){
        modelAndView.addObject("model", this.modelMapper.map(this.userService.findUserByName(principal.getName()), UserViewModel.class));

        return super.view("profile", modelAndView);
    }

    @GetMapping("/edit")
    @PreAuthorize("isAuthenticated()")
    public ModelAndView edit(Principal principal, ModelAndView modelAndView){
        modelAndView.addObject("model", this.modelMapper.map(this.userService.findUserByName(principal.getName()), UserViewModel.class));

        return super.view("edit-user", modelAndView);
    }

    @PatchMapping("/edit")
    @PreAuthorize("isAuthenticated()")
    public ModelAndView editConfirmed(@ModelAttribute UserEditBindingModel model){
        if (!model.getPassword().equals(model.getConfirmPassword())){
            return super.view("edit-user");
        }
        this.userService.editUser(this.modelMapper.map(model, UserServiceModel.class), model.getOldPassword());
        return super.redirect("/users/profile");
    }

    @GetMapping("/all")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ModelAndView allUsers(ModelAndView modelAndView){
        List<UsersAllViewModel> allUsers = this.userService.findAllUsers()
                .stream().map(u ->{
                    UsersAllViewModel user = this.modelMapper.map(u, UsersAllViewModel.class);
                    user.setAuthorities(u.getAuthorities().stream().map(a -> a.getAuthority()).collect(Collectors.toSet()));
                    return user;
                }).collect(Collectors.toList());

        modelAndView.addObject("users", allUsers);

        return super.view("all-users", modelAndView);
    }

    @PostMapping("/set-user/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ModelAndView setUser(@PathVariable String id){
        this.userService.setAuthority(id, "user");

        return super.redirect("/users/all");
    }

    @PostMapping("/set-moderator/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ModelAndView setModerator(@PathVariable String id){
        this.userService.setAuthority(id, "moderator");

        return super.redirect("/users/all");
    }

    @PostMapping("/set-admin/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ModelAndView setAdmin(@PathVariable String id){
        this.userService.setAuthority(id, "admin");

        return super.redirect("/users/all");
    }
}
