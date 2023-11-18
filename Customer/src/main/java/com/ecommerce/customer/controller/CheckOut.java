package com.ecommerce.customer.controller;

import com.ecommerce.library.dto.AddressDto;
import com.ecommerce.library.model.Address;
import com.ecommerce.library.model.Coupen;
import com.ecommerce.library.model.Customer;
import com.ecommerce.library.model.ShopingCart;
import com.ecommerce.library.service.AddressService;
import com.ecommerce.library.service.CustomerService;
import com.ecommerce.library.service.ShopCartService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

@Controller
public class CheckOut {

    AddressService addressService;
    CustomerService customerService;
    ShopCartService shopCartService;
@Autowired
    public CheckOut(AddressService addressService,CustomerService customerService,ShopCartService shopCartService) {
        this.addressService = addressService;
        this.customerService= customerService;
        this.shopCartService=shopCartService;

    }

    @GetMapping("/checkOut")
    public String showCheckOutPagee(Model model, Principal principal, HttpSession httpSession){
        String username=principal.getName();
        if(principal==null)
        {
            return "redirect:/loginPage";
        }


        Customer customer=customerService.findByEmail(username);

        List<ShopingCart> shopingCarts=shopCartService.findShopingCartByCustomer(username);
        Coupen coupen=new Coupen();
        model.addAttribute("coupen",coupen);

        if(shopingCarts.isEmpty()){

            return "redirect:/cart?empties";
        }
        for(ShopingCart cart:shopingCarts){
            int quantity=cart.getQuantity();
            int productQuantity=cart.getProduct().getCurrentQuantity();
            if(quantity>productQuantity){
                return "redirect:/cart?quantityError";
            }
        }



        double totel=shopCartService.grandTotel(username);
        List<Address> addresses=addressService.findAddressByCustomer(username);
        model.addAttribute("customer",customer);
        model.addAttribute("addresses",addresses);
        model.addAttribute("cartItem",shopingCarts);
        model.addAttribute("totel",totel);
        model.addAttribute("payable",totel);

    return "checkOut";
    }

    @GetMapping("/addAddress")
    public String showAddAddrss(Model model,Principal principal){
    if(principal==null){
        return "redirect:/loginPage";
    }
        AddressDto addressDto=new AddressDto();
        model.addAttribute("tittle","Add address");
        model.addAttribute("address",addressDto);
        return "login/add-address";
    }
    @PostMapping("/saveAddress")
    public String saveAddress(@ModelAttribute("address") AddressDto addressDto, Principal principal){
        if(principal==null){
            return "redirect:/loginPage";
        }

    String username=principal.getName();
    addressService.save(addressDto,username);
        return "redirect:/checkOut";
    }
    @GetMapping("/editAddress")
    public String showEditAddress(@RequestParam("addressId")Long id,Model model){
    Optional<Address> address=addressService.findByid(id);
    model.addAttribute("address",address);
    return "login/edit-address";
    }
    @PostMapping("/updateAddress")
    public String showEditAddress(@ModelAttribute("address")AddressDto addressDto){
        addressService.update(addressDto);
        return "redirect:/checkOut";
    }
}
