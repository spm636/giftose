package com.ecommerce.customer.controller;

import com.ecommerce.library.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class FilterController {
    private CategoryService categoryService;
@Autowired
    public FilterController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

}
