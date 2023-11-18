package com.ecommerce.admin.conroller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AdminConroller {
    @GetMapping("/loginPage")
    public String showLoginPage(HttpServletRequest request, Authentication authentication){
        HttpSession session= request.getSession();
        Object attribute=session.getAttribute("userLoginID");
        if(attribute!=null){
            return  "redirect:/adminHome";
        }

        return "login-page";
    }

}
