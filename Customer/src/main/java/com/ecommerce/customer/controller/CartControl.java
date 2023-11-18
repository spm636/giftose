package com.ecommerce.customer.controller;

import com.ecommerce.customer.config.CustomUser;
import com.ecommerce.library.dto.CoupenDto;
import com.ecommerce.library.model.Coupen;
import com.ecommerce.library.model.Customer;
import com.ecommerce.library.model.Product;
import com.ecommerce.library.model.ShopingCart;
import com.ecommerce.library.service.CoupenService;
import com.ecommerce.library.service.CustomerService;
import com.ecommerce.library.service.ProductService;
import com.ecommerce.library.service.ShopCartService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.naming.InsufficientResourcesException;
import java.security.Principal;
import java.util.List;

@Controller

public class CartControl {
    ShopCartService shopCartService;

    ProductService productService;
    CustomerService customerService;
    CoupenService coupenService;
@Autowired
    public CartControl(ShopCartService shopCartService, ProductService productService,
                       CustomerService customerService,CoupenService coupenService) {
        this.shopCartService = shopCartService;
        this.productService = productService;
        this.customerService = customerService;
        this.coupenService=coupenService;
    }

    @GetMapping("/cart")
    public String showCart(Model model,Principal principal){
    if (principal==null){
        return "redirect:/loginPage";
    }
        String username=principal.getName();
        List<ShopingCart> shopingCart=shopCartService.findShopingCartByCustomer(username);

        double totel=shopCartService.grandTotel(username);
        model.addAttribute("totel",totel);

        model.addAttribute("shopingCart",shopingCart);

    return "cart";
    }
    @GetMapping("/addToCart")
    public String showAddToCart(@RequestParam("productId")Long id, Authentication authentication,Principal principal)
    {
        if(principal==null){
            return "redirect:/loginPage";
        }

        int quantity=1;
        CustomUser customUser= (CustomUser) authentication.getPrincipal();

        shopCartService.addItemToCart(id,quantity, customUser.getCustomer_id());

        return "redirect:/cart";

    }
        @GetMapping("/deleteCartItem")
        public String showDelete(@RequestParam("cartId")Long id,Authentication authentication){
        CustomUser customUser= (CustomUser) authentication.getPrincipal();
        shopCartService.deleteById(id,customUser.getCustomer_id());
     return "redirect:/cart";
    }

        @GetMapping("/incrimentQuantity")
        public String showQuantityIncriment(@RequestParam("cartId")Long id,@RequestParam("productId") Long pId){
        shopCartService.incriment(id,pId);
            return "redirect:/cart";
        }
        @GetMapping("/decrimetQuantity")
         public String showQuntityDecriment(@RequestParam("cartId")Long id){
        shopCartService.decriment(id);
            return "redirect:/cart";
        }

}
