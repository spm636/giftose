package com.ecommerce.library.repository;

import com.ecommerce.library.model.Coupen;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CoupenRepository  extends JpaRepository<Coupen,Long> {
    @Query("select c from Coupen c where c.coupencode=?1")
    Coupen findByCoupenCode(String coupencode);
}
