package com.ecommerce.admin.conroller;

import com.ecommerce.library.dto.CoupenDto;
import com.ecommerce.library.model.Coupen;
import com.ecommerce.library.service.CoupenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;

@Controller
public class CoupenController {

    private CoupenService coupenService;
    @Autowired
    public CoupenController(CoupenService coupenService) {
        this.coupenService = coupenService;
    }

    @GetMapping("/Coupen")
    public String showCoupen(Model model){

        List<Coupen> coupens=coupenService.findAll();

        model.addAttribute("coupens",coupens);
        return "coupen";
    }
    @GetMapping("/addCoupen")
    public String ShowAddCoupen(Model model){
        CoupenDto coupenDto=new CoupenDto();
        model.addAttribute("coupen",coupenDto);
        return "add-coupen";
    }

    @PostMapping("/saveCoupen")
    public String showSaveCoupen(@ModelAttribute("coupen")CoupenDto coupenDto){
        coupenService.save(coupenDto);

        return "redirect:/Coupen";
    }

    @GetMapping("/disableCoupen")
    public String showDisableCoupen(@RequestParam("coupenId")Long id){

        coupenService.disableCoupen(id);
        return "redirect:/Coupen";
    }

    @GetMapping("/enableCoupen")
    public String showEnableCoupen(@RequestParam("coupenId")Long id){

        coupenService.enableCoupen(id);
        return "redirect:/Coupen";
    }
    @GetMapping("/editCoupen")
    public String showCoupenUpdate(@RequestParam("coupenId")Long id,Model model){
        Coupen coupen=coupenService.findByid(id);
        model.addAttribute("coupen",coupen);
        return "edit-coupen";
    }
    @PostMapping("/updateCoupen")
    public String updateCoupen(@ModelAttribute("coupen")CoupenDto coupenDto){
        coupenService.updateCoupen(coupenDto);
        return "redirect:/Coupen";
    }
}
