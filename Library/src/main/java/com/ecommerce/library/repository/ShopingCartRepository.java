package com.ecommerce.library.repository;

import com.ecommerce.library.model.OrderDetails;
import com.ecommerce.library.model.ShopingCart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ShopingCartRepository extends JpaRepository<ShopingCart,Long> {

    @Query("SELECT sc from ShopingCart sc where sc.customer.customer_id=?1 and sc.product.id=?2 and sc.deleted=false")
    ShopingCart findByUsersProduct(Long id, long productId);
    @Query("select sc from ShopingCart sc where sc.customer.email=?1 and sc.deleted=false")
    List<ShopingCart> findShopingCartByCustomer(String email);


    @Query("SELECT COALESCE(SUM(sc.totalPrice), 0) FROM ShopingCart sc WHERE sc.customer.customer_id = :id and sc.deleted=false")
   double findGrandTotel(Long id);
}
