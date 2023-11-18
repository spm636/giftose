package com.ecommerce.admin.conroller;

import com.ecommerce.library.dto.*;
import com.ecommerce.library.model.Order;
import com.ecommerce.library.model.OrderDetails;
import com.ecommerce.library.repository.OrderRepository;
import com.ecommerce.library.service.DashBoardService;
import com.ecommerce.library.service.OrderService;
import com.ecommerce.library.service.WalletService;
import com.ecommerce.library.utils.*;
import com.lowagie.text.DocumentException;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.security.Principal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
public class OrderDetailsControll {
    private OrderService orderService;
    private OrderRepository orderRepository;
    private WalletService walletService;
    private DashBoardService dashBoardService;
    private CsvGeneratorDaily csvGeneratorDaily;
    private CsvGeneratorMonthly csvGeneratorMonthly;
    private CsvGeneratorWeekly csvGeneratorWeekly;

    @Autowired
    public OrderDetailsControll(OrderService orderService, OrderRepository orderRepository,
                                WalletService walletService, DashBoardService dashBoardService,
                                CsvGeneratorDaily csvGeneratorDaily,CsvGeneratorMonthly csvGeneratorMonthly,
                                CsvGeneratorWeekly csvGeneratorWeekly) {
        this.orderService = orderService;
        this.orderRepository = orderRepository;
        this.walletService = walletService;
        this.dashBoardService = dashBoardService;
        this.csvGeneratorDaily=csvGeneratorDaily;
        this.csvGeneratorMonthly=csvGeneratorMonthly;
        this.csvGeneratorWeekly=csvGeneratorWeekly;
    }

    @GetMapping("/orderDetails/{pageNo}")
    public String showOrderDetails(@PathVariable("pageNo") int pageNo, Model model) {


        Page<Order> orders = orderService.findOrderByPageble(pageNo, 10);
        //List<Order>orders=orderService.findAll();
        OrderDto orderDto=new OrderDto();
        model.addAttribute("report",orderDto);


        model.addAttribute("orders", orders);
        model.addAttribute("currentPage", pageNo);
        model.addAttribute("totalPage", orders.getTotalPages());
        return "orderDetails";
    }

    @GetMapping("/orderDetailsInfo")
    public String showOrderDetaliInfo(@RequestParam("orderId") Long orderId, Model model) {
        List<OrderDetails> orderDetails = orderService.findByOrderId(orderId);
        Order order = orderService.findById(orderId);
        model.addAttribute("orderDetails", orderDetails);
        model.addAttribute("order", order);
        model.addAttribute("order1", order);
        return "orderdDetail-info";
    }

    @PostMapping("/updateStatus")
    public String showUpdateOrderStaus(@ModelAttribute("order1") Order order) {
        orderService.updateOrderStatus(order);
        if (order.getOrderStatus().equals("Return Accept")) {
            walletService.addToRefundAmount(order.getId());
        }
        return "redirect:/orderDetails/0";
    }

    @GetMapping("/pdfReport")
    public void generatePdf(@ModelAttribute("report")OrderDto orderDto, HttpServletResponse response, Principal principal) throws DocumentException, IOException {


        String value=orderDto.getPdfReport();
        if(value.equals("daily")){
            String email = principal.getName();
            response.setContentType("application/pdf");
            DateFormat dateFormat = new SimpleDateFormat("YYYY-MM-DD:HH:MM:SS");
            String currentDateTime = dateFormat.format(new Date());
            System.out.println(currentDateTime);
            String headerkey = "Content-Disposition";
            String headervalue = "attachment; filename=pdf_" + currentDateTime + ".pdf";
            response.setHeader(headerkey, headervalue);
            Date date=new Date();
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);

            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH) + 1;
            List<DailyEarning> list=orderService.dailyReport(year,month);


            PdfGenerator pdfGenerator=new PdfGenerator();
            pdfGenerator.setOrders(list);
            pdfGenerator.generate(response);

        }
        if(value.equals("weekly")){
            response.setContentType("application/pdf");
            DateFormat dateFormat = new SimpleDateFormat("YYYY-MM-DD:HH:MM:SS");
            String currentDateTime = dateFormat.format(new Date());
            System.out.println(currentDateTime);
            String headerkey = "Content-Disposition";
            String headervalue = "attachment; filename=pdf_" + currentDateTime + ".pdf";
            response.setHeader(headerkey, headervalue);
            Date date=new Date();
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);

            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH) + 1;


            List<WeeklyEarnings> list=orderService.findWeeklyEarnings(2023);


            WeeklyPdfGenerator pdfGenerator=new WeeklyPdfGenerator();
            pdfGenerator.setOrders(list);
            pdfGenerator.generate(response);
        }
        if(value.equals("monthly")){
            String email = principal.getName();
            response.setContentType("application/pdf");
            DateFormat dateFormat = new SimpleDateFormat("YYYY-MM-DD:HH:MM:SS");
            String currentDateTime = dateFormat.format(new Date());
            System.out.println(currentDateTime);
            String headerkey = "Content-Disposition";
            String headervalue = "attachment; filename=pdf_" + currentDateTime + ".pdf";
            response.setHeader(headerkey, headervalue);

            Date date=new Date();
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);

            int year = calendar.get(Calendar.YEAR);



            List<Monthlyearning> monthlyearnings = orderService.getMonthlyReport(year);

            MonthlyReportPdf monthlyReportPdf = new MonthlyReportPdf();
            monthlyReportPdf.setOrders(monthlyearnings);
            monthlyReportPdf.generate(response);
        }




    }



    @GetMapping("/csvReport")
    public ResponseEntity<byte[]> generateCsvFile(@ModelAttribute("report")OrderDto orderDto) {
        String value=orderDto.getCsvReport();
        if(value.equals("daily")){
            List<DailyEarning> dailyEarnings = orderService.dailyReport(2023,11);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
            headers.setContentDispositionFormData("attachment", "dailyreport.csv");

            byte[] csvBytes = csvGeneratorDaily.generateCsv(dailyEarnings).getBytes();

            return new ResponseEntity<>(csvBytes, headers, HttpStatus.OK);
        }
        else if(value.equals(("monthly"))){
            List<Monthlyearning> monthlyearnings=orderService.getMonthlyReport(2023);
            HttpHeaders headers=new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
            headers.setContentDispositionFormData("attachment","monthlyReport.csv");
            byte[] csvBytes=csvGeneratorMonthly.generateCsv(monthlyearnings).getBytes();
            return new ResponseEntity<>(csvBytes,headers,HttpStatus.OK);
        }
        else{
            List<WeeklyEarnings> weeklyEarnings=orderService.findWeeklyEarnings(2023);
            HttpHeaders headers=new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
            headers.setContentDispositionFormData("attachment","weeklyReport.csv");
            byte[] csvBytes=csvGeneratorWeekly.generateCsv(weeklyEarnings).getBytes();
            return new ResponseEntity<>(csvBytes,headers,HttpStatus.OK);

        }



    }
    @GetMapping("/orderStatus/{pageNo}")
    public String showOrderStatus(@PathVariable("pageNo")int pageNo,
                                  @ModelAttribute("report")OrderDto orderDto1,Model model){
        String orderStatus=orderDto1.getOrderStatus();
        Page<Order> orders = orderService.findOrderByOrderStatusPagable(pageNo,orderStatus);
        //List<Order>orders=orderService.findAll();
        OrderDto orderDto=new OrderDto();
        model.addAttribute("report",orderDto);


        model.addAttribute("orders", orders);
        model.addAttribute("currentPage", pageNo);
        model.addAttribute("totalPage", orders.getTotalPages());
        return "orderDetails";
    }


}