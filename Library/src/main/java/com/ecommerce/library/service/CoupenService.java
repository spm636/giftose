package com.ecommerce.library.service;

import com.ecommerce.library.dto.CoupenDto;
import com.ecommerce.library.model.Coupen;

import java.util.List;
import java.util.Optional;

public interface CoupenService {
    void save(CoupenDto coupenDto);

    List<Coupen> findAll();
    void enableCoupen(Long id);
    void disableCoupen(Long id);
    Coupen findByCoupenCode(String coupencode);

    void dicreseCoupen(long id);
    Coupen findByid(Long id);

    void updateCoupen(CoupenDto coupenDto);

}
