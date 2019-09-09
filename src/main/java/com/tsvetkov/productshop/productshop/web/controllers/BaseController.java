package com.tsvetkov.productshop.productshop.web.controllers;

import org.springframework.web.servlet.ModelAndView;

public abstract class BaseController {

    protected ModelAndView view(String view, ModelAndView modelAndView){
        modelAndView.setViewName(view);

        return modelAndView;
    }
    public ModelAndView view(String view){
        return this.view(view, new ModelAndView());
    }

    public ModelAndView redirect(String url){
        return this.view("redirect:"+ url);
    }
}
