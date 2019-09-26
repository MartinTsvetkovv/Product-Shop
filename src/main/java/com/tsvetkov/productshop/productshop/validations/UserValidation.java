package com.tsvetkov.productshop.productshop.validations;


import com.tsvetkov.productshop.productshop.domain.entities.User;
import com.tsvetkov.productshop.productshop.domain.models.service.UserServiceModel;

public interface UserValidation {

    boolean isValid(UserServiceModel userByName);
}
