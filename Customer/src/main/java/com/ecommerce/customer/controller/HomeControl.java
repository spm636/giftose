package com.ecommerce.customer.controller;

import com.ecommerce.library.dto.CategoryDto;
import com.ecommerce.library.dto.ProductDto;
import com.ecommerce.library.dto.ReviewDto;
import com.ecommerce.library.model.*;
import com.ecommerce.library.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
public class HomeControl {
    ProductService productService;
    CategoryService categoryService;

    CustomerService customerService;
    OrderService orderService;
    ReviewService reviewService;
    WalletService walletService;

    public HomeControl(ProductService productService, CategoryService categoryService,
                       CustomerService customerService,OrderService orderService,ReviewService reviewService,
                       WalletService walletService) {
        this.productService = productService;
        this.categoryService = categoryService;
        this.customerService=customerService;
        this.orderService=orderService;
        this.reviewService=reviewService;
        this.walletService=walletService;

    }

    @GetMapping("/")
    public String showMainPage(Model model) {

        List<Product> productDtos=productService.listViewProducts();

        model.addAttribute("product",productDtos);

        model.addAttribute("tittle","main");

        return "main";
    }
    @GetMapping("/about")
    public String showAbout(){
        return "about";
    }


    @GetMapping("/contact")
    public String showContact(){
        return "/contact";
    }
    @GetMapping("/shop")
    public String showshop(Model model){
        List<Product> productDtos=productService.products();

        model.addAttribute("product",productDtos);
        model.addAttribute("tittle","shop");
        CategoryDto category=new CategoryDto();
        model.addAttribute("categories1",category);
        List<Category> categories=categoryService.findAllByActivatedTrue();
        model.addAttribute("category",categories);

        return "/shop";
    }



    @GetMapping("/productDetails")
    public String showProductDetails(@RequestParam("productId")Long id,Model model){
        Product product=productService.getById(id);

        Double reviewDto=reviewService.findRatingByProduct(id);
        System.out.println("review rate="+reviewDto);
        List<Review> reviews=reviewService.readReviewByProduct(id);
        model.addAttribute("product",product);
        model.addAttribute("tittle","productDetails");
        model.addAttribute("rating",reviewDto);
        model.addAttribute("reviews",reviews);
        return "product-details";
}
    @GetMapping("/account")
    public String showAccount(Model model,Principal principal){
        if(principal==null){
            return "redirect:/loginPage";
        }
        String username=principal.getName();
        Customer customer=customerService.findByEmail(username);
        model.addAttribute("customer",customer);
       // List<Order>orderDetails=orderService.findOrderByCustomer(username);
      //  model.addAttribute("orderDetails",orderDetails);

        model.addAttribute("address",customer.getAddress());
        return "account";
    }

    @GetMapping("/cancelOrder")
    public String showCancelOrder(@ModelAttribute("orderId")Long id){
        Order order=orderService.findById(id);

        orderService.cancelOrder(id);
        if(order.getPaymentMethod().equals("online_payment") || order.getPaymentMethod().equals("wallet"))
        {
            walletService.addToRefundAmount(id);
        }
        return "redirect:/order";
    }
    @GetMapping("/returnOrder")
    public String showReturnOrder(@ModelAttribute("orderId") Long id){
        orderService.returnOrder(id);
        return "redirect:/order";
    }
    @PostMapping("/changePassword")
    public String showChangePassword(@ModelAttribute("customer") Customer customer){
        customerService.changePassword(customer);
        System.out.println(customer.getPassword());
        return "redirect:/account";
    }
    @GetMapping("/search")
    public String showSearch(@RequestParam("key")String key,Model model){
        List<Product>products=new ArrayList<>();
        products=productService.searchProducts(key);
        model.addAttribute("product",products);
        Category category=new Category();
        model.addAttribute("categories1",category);
        List<Category> categories=categoryService.findAllByActivatedTrue();
        model.addAttribute("category",categories);
        return "shop";
    }
    @GetMapping("/searchCategory")
    public String showCategoryFilter(@ModelAttribute("categories1")CategoryDto category,Model model){
        List<Product> products=productService.findAllByCategory(category.getName());
        model.addAttribute("product",products);
        CategoryDto category1=new CategoryDto();
        model.addAttribute("categories1",category1);
        List<Category> categories=categoryService.findAllByActivatedTrue();
        model.addAttribute("category",categories);
        return "shop";
    }





}
