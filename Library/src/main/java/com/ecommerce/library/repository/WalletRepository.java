package com.ecommerce.library.repository;

import com.ecommerce.library.model.Wallet;
import jdk.jfr.Registered;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface WalletRepository extends JpaRepository<Wallet,Long> {

    @Query("select w from Wallet w where w.customer.customer_id=?1")
    Wallet findByCustomer(Long customerId);
}
