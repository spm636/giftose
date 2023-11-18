package com.ecommerce.customer.controller;

import com.ecommerce.customer.config.CustomUser;
import com.ecommerce.library.model.Wallet;
import com.ecommerce.library.model.WalletHistory;
import com.ecommerce.library.service.WalletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;
import java.util.List;

@Controller
public class WalletController {

    private WalletService walletService;

    @Autowired
    public WalletController(WalletService walletService) {
        this.walletService = walletService;
    }

    @GetMapping("/wallet")
    public String showWallet(Principal principal, Model model, Authentication authentication){
        if(principal==null){
            return "redirect:/loginPage";
        }
        CustomUser customUser= (CustomUser) authentication.getPrincipal();
        Wallet wallet=walletService.findByCustomer(customUser.getCustomer_id());


        List<WalletHistory> walletHistories=walletService.findAllByCustomer(customUser.getCustomer_id());
        String name=customUser.getName();
        model.addAttribute("wallet",wallet);
        model.addAttribute("walletHistory",walletHistories);
        model.addAttribute("name",name);

        return "wallet";
    }
}
