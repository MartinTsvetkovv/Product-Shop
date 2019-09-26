package com.tsvetkov.productshop.productshop.validations;

import com.tsvetkov.productshop.productshop.domain.models.service.UserServiceModel;
import org.springframework.stereotype.Component;

@Component
public class UserValidationImpl implements UserValidation {

    @Override
    public boolean isValid(UserServiceModel userByName) {
        return userByName != null;
    }
}
