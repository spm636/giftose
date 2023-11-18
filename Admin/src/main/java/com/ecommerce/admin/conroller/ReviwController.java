package com.ecommerce.admin.conroller;

import com.ecommerce.library.model.Product;
import com.ecommerce.library.model.Review;
import com.ecommerce.library.service.ProductService;
import com.ecommerce.library.service.ReviewService;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class ReviwController {
    private ProductService productService;
    private ReviewService reviewService;

    public ReviwController(ProductService productService,ReviewService reviewService) {
        this.productService = productService;
        this.reviewService=reviewService;
    }

    @GetMapping("/review/{pageNo}")
    public String showRiew(@PathVariable("pageNo")int pageNo, Model model){
        Page<Product> products=productService.getAllProducts(pageNo);
        model.addAttribute("product",products);
        model.addAttribute("currentPage", pageNo);
        model.addAttribute("totalPage", products.getTotalPages());
        return "review";
    }
    @GetMapping("/showProductReview")
    public String showProductReview(@RequestParam("productId")Long productId,Model model){
        List<Review> reviews=reviewService.readReviewByProduct(productId);
        model.addAttribute("reviews",reviews);
        return "product-review";
    }
    @GetMapping("/rating")
    public String rating(){
        return "rating";
    }
}
