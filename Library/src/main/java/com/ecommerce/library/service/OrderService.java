package com.ecommerce.library.service;

import com.ecommerce.library.dto.*;
import com.ecommerce.library.model.Order;
import com.ecommerce.library.model.OrderDetails;
import com.ecommerce.library.model.ShopingCart;
import org.springframework.data.domain.Page;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

public interface OrderService {
    Order saveOrder(ShopingCart shopingCart, String email, Long addressId,String paymentMethod,Double grandTotel);
    List<OrderDetails> findAllOrder();

    List<OrderDetails> findOrderDetailsByCustomer(String email);

    List<OrderDetails> findByOrderId(Long orderid);

    Order findById(long id);

    void updateOrderStatus(Order order);

    List<Order> findAll();

    void cancelOrder(Long id);

    void returnOrder(Long id);
    List<Order> findOrderByCustomer(String email);
    public List<Order> getDailyOrders(LocalDate date);

    Page<Order> findOrderByPageble(int page,int size);
    Page<Order> findOrderByCustomerPagable(int pageNo,String email);
    Page<Order> findOrderByOrderStatusPagable(int pageNo,String status);
    List<Order> getDailyReport(Date date);
    List<Monthlyearning> getMonthlyReport(int year);
    List<DailyEarning> dailyReport(int year, int month);
    List<WeeklyEarnings> findWeeklyEarnings(int year);

}
