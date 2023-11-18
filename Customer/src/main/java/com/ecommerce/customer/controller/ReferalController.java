package com.ecommerce.customer.controller;

import com.ecommerce.library.dto.CustomerDto;
import com.ecommerce.library.model.Customer;
import com.ecommerce.library.service.CustomerService;
import com.ecommerce.library.service.WalletService;
import com.ecommerce.library.utils.Utility;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import jakarta.servlet.http.HttpServletRequest;
import net.bytebuddy.utility.RandomString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.io.UnsupportedEncodingException;
import java.security.Principal;

@Controller
public class ReferalController {
    @Autowired
    JavaMailSender mailSender;
    @Autowired
    CustomerService customerService;
    @Autowired
    WalletService walletService;
    @PostMapping("/sendReferal")
    public String processReferalCode(HttpServletRequest request, Model model,Principal principal) throws MessagingException, UnsupportedEncodingException {
        String email = request.getParameter("email");
        String token = RandomString.make(30);
        String username=principal.getName();

        customerService.updateReferalCodeToken(token, username);

        String referalLink = Utility.getSiteURL(request) + "/referal_link?token=" + token;
        sendEmail(email, referalLink);
        model.addAttribute("message", "We have sent a referal link to  email. Please check.");



        return "redirect:/account";

    }
    @GetMapping("/referal_link")

    public String showReferalLogin(@Param(value = "token") String token, Model model) {
        CustomerDto customerDto=new CustomerDto();

        model.addAttribute("token", token);

        model.addAttribute("users",customerDto);



        return "referal-signUp";

    }
    @PostMapping("/registration1")
    public String showRegisterReferalUser(@ModelAttribute("users")CustomerDto customerDto,
                                          HttpServletRequest request){

        String token = request.getParameter("token");
        Customer customer=customerService.getByReferalToken(token);
        Customer customer1=customerService.findByEmail(customerDto.getEmail());

        if(customer1!=null){
            return "redirect:/referal_link?exist&token="+token;
        }

        customerService.saveCustomer(customerDto);
        walletService.addWalletToReferalEarn(customer.getCustomer_id());

        return "redirect:/referal_link?success";
    }
    public void sendEmail(String recipientEmail, String link)
            throws MessagingException, UnsupportedEncodingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);

        helper.setFrom("contact@shopme.com", "Gift Shop");
        helper.setTo(recipientEmail);

        String subject = "Here's the link to login gift Shop";

        String content = "<p>Hello,</p>"
                + "<p>You have requested to Login Gift shop.</p>"
                + "<p>Click the link below to loggin andsignup page:</p>"
                + "<p><a href=\"" + link + "\">myGiftShope</a></p>"
                + "<br>"
                + "<p>Ignore this email if you already have account, "
                + "or you have not made the request.</p>";

        helper.setSubject(subject);

        helper.setText(content, true);

        mailSender.send(message);
    }
}
