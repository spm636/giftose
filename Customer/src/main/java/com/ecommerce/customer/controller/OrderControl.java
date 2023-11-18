package com.ecommerce.customer.controller;

import com.ecommerce.customer.config.CustomUser;
import com.ecommerce.library.model.Order;
import com.ecommerce.library.model.OrderDetails;
import com.ecommerce.library.model.ShopingCart;
import com.ecommerce.library.model.Wallet;
import com.ecommerce.library.service.AddressService;
import com.ecommerce.library.service.OrderService;
import com.ecommerce.library.service.ShopCartService;
import com.ecommerce.library.service.WalletService;
import com.ecommerce.library.utils.PdfGenerator;
import com.lowagie.text.DocumentException;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;
import jakarta.servlet.http.HttpServletResponse;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.security.Principal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Controller
public class OrderControl {

    OrderService orderService;
    AddressService addressService;
    ShopCartService shopCartService;
    WalletService walletService;

    @Autowired
    public OrderControl(OrderService orderService, AddressService addressService,
                        ShopCartService shopCartService, WalletService walletService) {
        this.orderService = orderService;
        this.addressService = addressService;
        this.shopCartService = shopCartService;
        this.walletService=walletService;
    }


    @GetMapping("/orderConfirm")
    public String showOrderConfirm(){
        return "order-confirm";
    }

    @GetMapping("/order")
    public String showOrder(@RequestParam("pageNo")int pageNo, Model model, Principal principal) {
        if (principal == null) {
            return "redirect:/loginPage";
        }
        String username = principal.getName();
        Page<Order> orders = orderService.findOrderByCustomerPagable(pageNo,username);
        model.addAttribute("orders", orders);
       model.addAttribute("currentPage", pageNo);
       model.addAttribute("totalPage", orders.getTotalPages());

        return "orders";
    }

    @GetMapping("/orderDetails")
    public String showOrderDetails(@RequestParam("orderId") Long id, Model model) {
        List<OrderDetails> orderDetails = orderService.findByOrderId(id);
        model.addAttribute("orderDetails", orderDetails);
        return "order-details";
    }


    @PostMapping("/createPayment")
    @ResponseBody
    public String showOnlinePayment(Principal principal, Authentication authentication,
                                    @RequestBody Map<String, Object> data) throws RazorpayException {
        CustomUser customUser= (CustomUser) authentication.getPrincipal();
        Long id=customUser.getCustomer_id();
        String username=principal.getName();
        String paymentMethod = data.get("paymentMethod").toString();
        Long address_id=Long.parseLong(data.get("addressId").toString());


        Double amount= Double.valueOf(data.get("amount").toString());

        System.out.println(amount);

        if(paymentMethod.equals("online_payment")) {
            ShopingCart shopingCart = new ShopingCart();
            orderService.saveOrder(shopingCart, username, address_id,paymentMethod,amount);
            var client = new RazorpayClient("rzp_test_CFE7czt9lfmfzb", "iAYrUy4eegkNE6zJke0NBfCM");
            JSONObject object = new JSONObject();
            object.put("amount", amount * 100);
            object.put("currency", "INR");
            object.put("receipt", "receipt#1");
            com.razorpay.Order order = client.orders.create(object);
            System.out.println(order);
            System.out.println(paymentMethod);
            System.out.println(address_id);
            return order.toString();

        }
        if(paymentMethod.equals("wallet")){
            Wallet wallet=walletService.findByCustomer(id);
            if(wallet.getBalance()<amount){
                JSONObject option=new JSONObject();
                option.put("status","noWallet");
                return option.toString();
            }
            else{
                ShopingCart shopingCart = new ShopingCart();
                orderService.saveOrder(shopingCart, username, address_id,paymentMethod,amount);
                walletService.setWalletAmount(id,amount);
                JSONObject option=new JSONObject();
                option.put("status","wallet");
                return option.toString();
            }

        }
        else{
            ShopingCart shopingCart = new ShopingCart();
            orderService.saveOrder(shopingCart, username, address_id,paymentMethod,amount);

            JSONObject option=new JSONObject();
            option.put("status","cash");
            return option.toString();
        }


    }
    @PostMapping("/verify-payment")
    @ResponseBody
    public String showVerifyPayment(@RequestBody Map<String,Object> data){

        return "done";
    }
    @GetMapping("/orderListPdf1")
    public void generatePdf(HttpServletResponse response, Principal principal) throws DocumentException, IOException {

        String email=principal.getName();
        response.setContentType("application/pdf");
        DateFormat dateFormat = new SimpleDateFormat("YYYY-MM-DD:HH:MM:SS");
        String currentDateTime = dateFormat.format(new Date());
        String headerkey = "Content-Disposition";
        String headervalue = "attachment; filename=pdf_" + currentDateTime + ".pdf";
        response.setHeader(headerkey, headervalue);

       // List<Order> list=orderService.findOrderByCustomer(email);

        PdfGenerator pdfGenerator=new PdfGenerator();
       // pdfGenerator.setOrders(list);
        pdfGenerator.generate(response);
    }


}
