package com.tsvetkov.productshop.productshop.errors;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.lang.reflect.Method;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Category was not found")
public class CategoryNotFoundException extends Throwable {

    private int statusCode;

    public CategoryNotFoundException(String message) {
        super(message);
        this.statusCode = HttpStatus.NOT_FOUND.value();
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }
}
