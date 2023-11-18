package com.ecommerce.customer.controller;

import com.ecommerce.library.dto.AddressDto;
import com.ecommerce.library.model.Address;
import com.ecommerce.library.service.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.util.Optional;

@Controller
public class AccountControll {

    AddressService addressService;
    @Autowired
    public AccountControll(AddressService addressService) {
        this.addressService = addressService;
    }
        @GetMapping("/accontAddAddress")
    public String showAccountAddAddress(Principal principal, Model model){
        if(principal==null){
            return "redirect:/loginPage";
        }
            AddressDto addressDto=new AddressDto();
        model.addAttribute("address",addressDto);
        return "account-addAddress";
        }
        @PostMapping("/saveAddressaccount")
    public String showSaveAccountaddress(@ModelAttribute("address")AddressDto addressDto,Principal principal){
        String username=principal.getName();
        addressService.save(addressDto,username);
        return "redirect:/account";
        }
        @GetMapping("/editCustomerAddress")
    public String showEditcustomerAddress(@RequestParam("addressId")Long id,Model model){
            Optional<Address> address=addressService.findByid(id);
            model.addAttribute("addrss",address);
        return "edit-account-address";
        }
        @PostMapping("/updateacountAddress")
    public String showupdateCustomerAddress(@ModelAttribute("address")AddressDto addressDto){
        addressService.update(addressDto);
        return "redirect:/account";
        }


}
