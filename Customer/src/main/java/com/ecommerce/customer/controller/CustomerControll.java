package com.ecommerce.customer.controller;

import com.ecommerce.library.dto.CategoryDto;
import com.ecommerce.library.dto.CustomerDto;
import com.ecommerce.library.model.Customer;
import com.ecommerce.library.model.UserOtp;
import com.ecommerce.library.repository.CustomerRepository;
import com.ecommerce.library.service.CustomerService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class CustomerControll {
    private CustomerService customerService;
    private CustomerRepository customerRepository;

@Autowired
    public CustomerControll(CustomerService customerService, CustomerRepository customerRepository) {
        this.customerService = customerService;
        this.customerRepository = customerRepository;
    }





    @GetMapping("/signup")
    public  String showSignUp(Model model){
        String email = (String) model.asMap().get("email");
        CustomerDto users=new CustomerDto();
        users.setEmail(email);
        model.addAttribute("users",users);
        return "login/register";
    }



    @PostMapping("/registration")
    public String saveRegister(@Valid @ModelAttribute("users") CustomerDto customerDto,
                               BindingResult result, Model model) {
       /* Customer existing = customerService.findByEmail(customerDto.getEmail());
        if (existing!=null) {

            return "redirect:/signup?exist";
        }
       if (result.hasErrors()) {
            model.addAttribute("users", customerDto);
            return "redirect:/signup?error";
        } */


        customerService.saveCustomer(customerDto);
        return "redirect:/signup?success";


    }
    @GetMapping("/loginPage")
    public String showLoginPage(HttpServletRequest request, Authentication authentication){
        HttpSession session= request.getSession();
        Object attribute=session.getAttribute("userLoginID");
        if(attribute!=null) {
            return "redirect:/";
        }
        CustomerDto customerDto=new CustomerDto();
        if(customerDto.isBlocked()==true)
            return "redirect:/loginPage?blocked";



    return "login/login-page";
    }
    @GetMapping("/verifyEmail")
    public String showVerifyEmail(){
        return "login/verifyEmail";
    }
    @GetMapping("/otpvalidation")
    public String showotpvalidationPage(Model model, HttpSession session){
        String email = (String) model.asMap().get("email");
        UserOtp userOTP = new UserOtp();
        userOTP.setEmail(email);
        session.setAttribute("email",email);
        model.addAttribute("userOTP",userOTP);
        return "login/otpvalidation";
    }
}
