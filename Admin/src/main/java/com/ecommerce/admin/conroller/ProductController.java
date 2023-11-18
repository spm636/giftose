package com.ecommerce.admin.conroller;

import com.ecommerce.library.dto.ProductDto;
import com.ecommerce.library.model.Category;
import com.ecommerce.library.model.Product;
import com.ecommerce.library.service.CategoryService;
import com.ecommerce.library.service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller

public class ProductController {

   private final ProductService productService;
   private final CategoryService categoryService;
@Autowired
    public ProductController(ProductService productService, CategoryService categoryService) {
        this.productService = productService;
        this.categoryService = categoryService;
    }

    @GetMapping("/productList/{pageNo}")
    public String showProductList(@PathVariable("pageNo")int pageNo, Model model){


        Page<Product> products=productService.getAllProducts(pageNo);
        List<Category> categories=categoryService.findAllByActivatedTrue();
        model.addAttribute("category",categories);

        Category category=new Category();
        model.addAttribute("categories1",category);

        model.addAttribute("product",products);
        model.addAttribute("currentPage", pageNo);
        model.addAttribute("totalPage", products.getTotalPages());



        return "product-list";
    }

    @GetMapping("/addProduct")
    public String showAddProduct(Model model){
        List<Category> categories=categoryService.findAllByActivatedTrue();
        ProductDto productDto=new ProductDto();
        model.addAttribute("category",categories);
        model.addAttribute("product",productDto);


        return "add-product";
    }

    @GetMapping("/updateProduct")
    public String showUpdateProduct(@RequestParam("productId")Long id,Model model){
    List<Category>category=categoryService.findAllByActivatedTrue();
    Product productDto=productService.findById(id);
    model.addAttribute("product",productDto);
    model.addAttribute("category",category);
    return "update-product";
    }



    @PostMapping("/saveProduct")
    public String saveProduct(@Valid @ModelAttribute("product")ProductDto productDto,
                    BindingResult result){
        Product existing= productService.findByName(productDto.getName());
        if (existing != null) {
            return "redirect:/addProduct?exist";
        }

        productService.save(productDto);
        return "redirect:/productList/0";
    }

    @PostMapping("/updateProduct")
    public String updateProduct(@ModelAttribute("product")ProductDto productDto) {
        productService.update(productDto);
        return "redirect:/productList/0";
    }


    @GetMapping("delete-product")
    public String deleteProduct(@RequestParam("productId")Long id){
productService.deleteById(id);
    return "redirect:/productList/0";
    }
    @GetMapping("enable-product")
    public String enableProduct(@RequestParam("productId")Long id){
    productService.enableById(id);
    return "redirect:/productList/0";
    }

    @GetMapping("/searchProduct")
    public String showSearchProduct(@RequestParam(name = "key", required = false)String key,Model model){
        List<Product>products=new ArrayList<>();
        products=productService.searchProducts(key);
        List<Category> categories=categoryService.findAllByActivatedTrue();
        model.addAttribute("category",categories);
        model.addAttribute("product",products);

        Category category=new Category();
        model.addAttribute("categories1",category);

        return "product-list";
    }
    @GetMapping("/searchCategory")
    public String showCategorySearch(@ModelAttribute("categories1")Category category,Model model){
    List<Product> products=productService.findAllByCategory(category.getName());
    model.addAttribute("product",products);
        List<Category> categories=categoryService.findAllByActivatedTrue();
        model.addAttribute("category",categories);
        Category category1=new Category();
        model.addAttribute("categories1",category1);
        
    return "product-list";
    }

}
