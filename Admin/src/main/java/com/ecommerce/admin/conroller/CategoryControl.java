package com.ecommerce.admin.conroller;

import com.ecommerce.library.dto.CategoryDto;
import com.ecommerce.library.dto.CustomerDto;
import com.ecommerce.library.model.Category;
import com.ecommerce.library.service.CategoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class CategoryControl {
    CategoryService categoryService;
@Autowired
    public CategoryControl(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping("/categoriesList")
    public String showCategory(Model model, Principal principal) {
        if (principal == null) {
            return "redirect:/login";
        }

    List<Category> categories=categoryService.findALl();


    model.addAttribute("categories",categories);




        return "categories";

    }
    @GetMapping("/categoryUpdate")
    public String findById(@RequestParam("CategoryId") Long id, Model model){
    Optional<Category> category=categoryService.findById(id);
    model.addAttribute("categories",category);

    return "updateCategory";
    }

    @GetMapping("/addCategory")
    public String showAddCategory(Model model){
        CategoryDto categoryDto=new CategoryDto();
        model.addAttribute("categories",categoryDto);
        return "add-category";
    }

    @PostMapping("/saveCategory")
    public String saveCategory(@Valid @ModelAttribute("categories")CategoryDto categoryDto,
                               BindingResult result){
        Category existing=categoryService.findByName(categoryDto.getName());
        if (existing!=null) {

            return "redirect:/addCategory?exist";
        }
            categoryService.saveCategory(categoryDto);
        return "redirect:/categoriesList";
    }

    @GetMapping("/delete-category")
    public String delete(@RequestParam("categoryID") Long theID) {

            categoryService.deleteById(theID);


        return "redirect:/categoriesList";
    }
    @GetMapping( "/enable-category")
    public String enable(@RequestParam("categoryID") Long theID) {

            categoryService.enableById(theID);


        return "redirect:/categoriesList";
    }



    @PostMapping("/update")
    public String updateCategory( @ModelAttribute("category")CategoryDto category
                                 ){

    categoryService.update(category);
    return "redirect:/categoriesList";
}




}
