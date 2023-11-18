package com.ecommerce.library.service;

import com.ecommerce.library.model.OrderDetails;
import com.ecommerce.library.model.ShopingCart;

import java.util.List;

public interface ShopCartService {
     ShopingCart addItemToCart(Long productId, int quantity, Long id);

    List<ShopingCart> findShopingCartByCustomer(String email);
    void deleteById(long id,Long customerId);
  Double grandTotel(String username);

  void incriment(Long id,Long productId);
  void decriment(Long id);


}

