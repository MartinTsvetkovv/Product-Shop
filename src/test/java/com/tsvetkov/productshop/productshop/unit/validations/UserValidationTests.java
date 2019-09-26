package com.tsvetkov.productshop.productshop.unit.validations;

import com.tsvetkov.productshop.productshop.domain.models.service.UserServiceModel;
import com.tsvetkov.productshop.productshop.validations.UserValidation;
import com.tsvetkov.productshop.productshop.validations.UserValidationImpl;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class UserValidationTests {

    private UserValidation userValidation;

    @Before
    public void setUp() {
        this.userValidation = new UserValidationImpl();
    }

    @Test
    public void userValidationIsValid() {
        String username = "User 1";
        boolean valid = this.userValidation.isValid(new UserServiceModel() {{
            setUsername(username);
        }});

        assertTrue(valid);
    }
    @Test
    public void UserValidationIsNotValid(){
        boolean valid = this.userValidation.isValid(null);
        assertFalse(valid);

    }


}
