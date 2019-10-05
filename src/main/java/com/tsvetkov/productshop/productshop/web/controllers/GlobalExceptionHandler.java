package com.tsvetkov.productshop.productshop.web.controllers;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class GlobalExceptionHandler extends BaseController {

    @ExceptionHandler(Throwable.class)
    public ModelAndView handleProductException(Throwable e) {
        ModelAndView modelAndView = new ModelAndView("error");
        modelAndView.addObject("message", e.getMessage());
        //modelAndView.addObject("statusCode", e.getStatusCode());
        return modelAndView;
    }

//    @ExceptionHandler(Throwable.class)
//    public ModelAndView handleProductException1(Throwable e) {
//        ModelAndView modelAndView = new ModelAndView("error");
//        modelAndView.addObject("message", e.getMessage());
//
//        Throwable throwable = e;
//
//        while(throwable.getCause() != null){
//            throwable = throwable.getCause();
//        }
//        modelAndView.addObject("message", throwable.getMessage());
//        return modelAndView;
//    }


}
