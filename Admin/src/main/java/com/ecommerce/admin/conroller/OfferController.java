package com.ecommerce.admin.conroller;

import com.ecommerce.library.dto.OfferDto;
import com.ecommerce.library.model.Category;
import com.ecommerce.library.model.Offer;
import com.ecommerce.library.model.Product;
import com.ecommerce.library.service.CategoryService;
import com.ecommerce.library.service.OfferService;
import com.ecommerce.library.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class OfferController {

    ProductService productService;
    OfferService offerService;
    CategoryService categoryService;
@Autowired
    public OfferController(ProductService productService,OfferService offerService,
                           CategoryService categoryService) {
        this.productService = productService;
        this.offerService=offerService;
        this.categoryService=categoryService;
    }

    @GetMapping("/offers")
    public String showOfferPage(Model model){

        List<Offer> offers=offerService.findAllOffer();
        model.addAttribute("offers",offers);


        return "offer";
    }
    @PostMapping("/saveOffer")
    public String showSaveOffer(@ModelAttribute("offer")OfferDto offerDto){
        offerService.saveOffer(offerDto);
        return "redirect:/offers";
    }
    @GetMapping("/enableOffer")
    public String showEnableOffer(@RequestParam("offerId")Long id){
    offerService.enableOffer(id);
    return "redirect:/offers";
    }
    @GetMapping("/disableOffer")
    public String showDisableOffer(@RequestParam("offerId")Long id){
    offerService.disableOffer(id);
        return "redirect:/offers";
    }
    @GetMapping("/productOffer")
    public String addProductOffer(Model model){
        OfferDto offerDto=new OfferDto();
        model.addAttribute("offer",offerDto);

        List<Product>  product=productService.findAllProduct();
        model.addAttribute("product",product);



    return "add-productOffer";
    }

    @GetMapping("/categoryOffer")
    public String addCategoryOffer(Model model){

        OfferDto offerDto=new OfferDto();
        model.addAttribute("offer",offerDto);
        List<Category> categories=categoryService.findAllByActivatedTrue();
        model.addAttribute("categories",categories);
    return "Category-offer";
    }
    @GetMapping("/editOffer")
    public String showEditoffer(@RequestParam("offerId")Long id,Model model){
    Offer offer=offerService.findById(id);
    model.addAttribute("offer",offer);
    return "edit-offer";
    }
    @PostMapping("/updateOffer")
    public String updateOffer(@ModelAttribute("offer")OfferDto offerDto){
    offerService.updateOffer(offerDto);
        return "redirect:/offers";
    }

}
