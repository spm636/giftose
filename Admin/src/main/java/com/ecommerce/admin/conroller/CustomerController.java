package com.ecommerce.admin.conroller;

import com.ecommerce.library.model.Customer;
import com.ecommerce.library.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class CustomerController {
    private CustomerService customerService;
@Autowired
    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping("/customerDetails")
    public String showCustomerDetails(Model model){
        List<Customer> customers=customerService.findAll();
        model.addAttribute("users",customers);
        return "customer-list";
    }
    @GetMapping("/block-customer")
    public String delete(@RequestParam("customerID") Long theID) {

        customerService.blockById(theID);


        return "redirect:/customerDetails";
    }
    @GetMapping( "/enable-customer")
    public String enable(@RequestParam("customerID") Long theID) {

        customerService.enableById(theID);


        return "redirect:/customerDetails";
    }
}
