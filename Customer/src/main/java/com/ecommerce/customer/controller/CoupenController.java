package com.ecommerce.customer.controller;

import com.ecommerce.library.dto.CoupenDto;
import com.ecommerce.library.model.Address;
import com.ecommerce.library.model.Coupen;
import com.ecommerce.library.model.Customer;
import com.ecommerce.library.model.ShopingCart;
import com.ecommerce.library.service.AddressService;
import com.ecommerce.library.service.CoupenService;
import com.ecommerce.library.service.CustomerService;
import com.ecommerce.library.service.ShopCartService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.security.Principal;
import java.util.List;

@Controller
public class CoupenController {
    ShopCartService shopCartService;
    CoupenService coupenService;
    AddressService addressService;
    CustomerService customerService;

    public CoupenController(ShopCartService shopCartService, CoupenService coupenService,
                            AddressService addressService, CustomerService customerService) {
        this.shopCartService = shopCartService;
        this.coupenService = coupenService;
        this.addressService = addressService;
        this.customerService = customerService;
    }
    @PostMapping("/applyCoupen")
    public String showApplyCoupen(@ModelAttribute("coupen") CoupenDto coupenDto, Principal principal,
                                  Model model){

        String username=principal.getName();
        Customer customer=customerService.findByEmail(username);
        List<ShopingCart> shopingCarts=shopCartService.findShopingCartByCustomer(username);
        Coupen coupen=coupenService.findByCoupenCode(coupenDto.getCoupencode());
        if(coupen.isExpired()==true){
            return "redirect:/checkOut?expired";
        }
        if(coupen==null){
           return "redirect:/checkOut";
        }

        double grandTotel=shopCartService.grandTotel(username);
        List<Address> addresses=addressService.findAddressByCustomer(username);
        double payableAmount;
        if(grandTotel<coupen.getMinimumOrderAmount())
        {
            model.addAttribute("errorMessage","order amount is less. ");
            model.addAttribute("addresses",addresses);
            model.addAttribute("cartItem",shopingCarts);
            model.addAttribute("totel",grandTotel);
            model.addAttribute("customer",customer);
            model.addAttribute("payable",grandTotel);
            return "checkOut";
        }
        double offerPercentage= Double.parseDouble(coupen.getOfferPercentage());
        double offer= (grandTotel * offerPercentage) / 100;
        if(offer<coupen.getMaximumOfferAmount()) {
           payableAmount  = grandTotel - offer;
        }
        else{
            payableAmount=grandTotel-100;
        }
        coupenService.dicreseCoupen(coupen.getId());
        model.addAttribute("addresses",addresses);
        model.addAttribute("cartItem",shopingCarts);
        model.addAttribute("totel",grandTotel);
        model.addAttribute("customer",customer);
        model.addAttribute("payable",payableAmount);
        System.out.println(payableAmount);
        return "checkOut";
    }
}
