package com.ecommerce.library.serviceImpliment;

import com.ecommerce.library.model.Customer;
import com.ecommerce.library.model.OrderDetails;
import com.ecommerce.library.model.Product;
import com.ecommerce.library.model.ShopingCart;
import com.ecommerce.library.repository.CustomerRepository;
import com.ecommerce.library.repository.ShopingCartRepository;
import com.ecommerce.library.service.CustomerService;
import com.ecommerce.library.service.ProductService;
import com.ecommerce.library.service.ShopCartService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ShopingCartServiceImpl implements ShopCartService {
   private ShopingCartRepository shopingCartRepository;
   private CustomerService customerService;
   private ProductService productService;

   private CustomerRepository customerRepository;




    public ShopingCartServiceImpl(ShopingCartRepository shopingCartRepository, CustomerService customerService,
                                  ProductService productService,CustomerRepository customerRepository
                                  ) {
        this.shopingCartRepository = shopingCartRepository;
        this.customerService = customerService;
        this.productService = productService;
        this.customerRepository=customerRepository;

    }

    @Override
    public ShopingCart addItemToCart(Long productId, int quantity, Long customerId) {
        ShopingCart shopingCart=shopingCartRepository.findByUsersProduct(customerId,productId);
        Product product=productService.getById(productId);


    if (shopingCart != null) {

            int oldQuantity = shopingCart.getQuantity();
            shopingCart.setQuantity(oldQuantity + quantity);
            shopingCart.setUnitPrice(product.getSalePrice());
            double totelPrice = product.getSalePrice() * (oldQuantity + quantity);
            shopingCart.setTotalPrice(totelPrice);
            shopingCart.setDeleted(false);

    }

    else {

            shopingCart = new ShopingCart();

            shopingCart.setProduct(product);
            shopingCart.setCustomer(customerRepository.getById(customerId));
            shopingCart.setQuantity(quantity);
            shopingCart.setUnitPrice(product.getSalePrice());
            double totelPrice = shopingCart.getUnitPrice() * shopingCart.getQuantity();
            shopingCart.setTotalPrice(totelPrice);
            shopingCart.setDeleted(false);

    }




        return shopingCartRepository.save(shopingCart);
    }

    @Override
    public List<ShopingCart> findShopingCartByCustomer(String email) {
        return shopingCartRepository.findShopingCartByCustomer(email);
    }

    @Override
    public void deleteById(long id,Long customerId) {
        ShopingCart shopingCart=shopingCartRepository.getReferenceById(id);

        shopingCart.setDeleted(true);
        shopingCartRepository.save(shopingCart);

    }

    @Override
    public Double grandTotel(String username) {
        Customer customer = customerRepository.findByEmail(username);

        if (customer != null) {
            Long customerId = customer.getCustomer_id();
            return shopingCartRepository.findGrandTotel(customerId);
        }


        return 0.0;
    }

    @Override
    public void incriment(Long id,Long productid) {
        ShopingCart shopingCart1=shopingCartRepository.getReferenceById(id);
        Product product=productService.getById(productid);
        if(product.getCurrentQuantity()>shopingCart1.getQuantity()) {
            shopingCart1.setQuantity(shopingCart1.getQuantity() + 1);
            shopingCart1.setTotalPrice(shopingCart1.getQuantity() * shopingCart1.getUnitPrice());
            shopingCartRepository.save(shopingCart1);
        }
    }

    @Override
    public void decriment(Long id) {
        ShopingCart shopingCart1=shopingCartRepository.getReferenceById(id);
        if(shopingCart1.getQuantity()>1) {
            shopingCart1.setQuantity(shopingCart1.getQuantity() - 1);
            shopingCart1.setTotalPrice(shopingCart1.getQuantity() * shopingCart1.getUnitPrice());
            shopingCartRepository.save(shopingCart1);
        }
    }
}
