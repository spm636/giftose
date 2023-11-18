package com.ecommerce.library.serviceImpliment;

import com.ecommerce.library.dto.CoupenDto;
import com.ecommerce.library.model.Coupen;
import com.ecommerce.library.repository.CoupenRepository;
import com.ecommerce.library.service.CoupenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CoupenServiceImpl implements CoupenService {

    private CoupenRepository coupenRepository;
    @Autowired
    public CoupenServiceImpl(CoupenRepository coupenRepository) {
        this.coupenRepository = coupenRepository;
    }

    @Override
    public void save(CoupenDto coupenDto) {
        Coupen coupen=new Coupen();
        coupen.setCoupencode(coupenDto.getCoupencode());
        coupen.setCoupenDescription(coupenDto.getCoupenDescription());
        coupen.setOfferPercentage(coupenDto.getOfferPercentage());
        coupen.setMinimumOrderAmount(coupenDto.getMinimumOrderAmount());
        coupen.setMaximumOfferAmount(coupenDto.getMaximumOfferAmount());
        coupen.setStartDate(coupenDto.getStartDate());
        coupen.setExpireDate(coupenDto.getExpireDate());
        coupen.setCount(coupenDto.getCount());
        coupen.setEnabled(true);
        coupenRepository.save(coupen);
    }

    @Override
    public void updateCoupen(CoupenDto coupenDto) {
        Coupen coupen=coupenRepository.getReferenceById(coupenDto.getId());
        if(coupenDto.getStartDate()!=null){
            coupen.setStartDate(coupenDto.getStartDate());
        }
        else{
            coupen.setStartDate(coupen.getStartDate());
        }
        if(coupenDto.getExpireDate()!=null){
            coupen.setExpireDate(coupenDto.getExpireDate());
        }
        else{
            coupen.setExpireDate(coupen.getExpireDate());
        }

        coupen.setCoupencode(coupenDto.getCoupencode());
        coupen.setCoupenDescription(coupenDto.getCoupenDescription());
        coupen.setOfferPercentage(coupenDto.getOfferPercentage());
        coupen.setMinimumOrderAmount(coupenDto.getMinimumOrderAmount());
        coupen.setMaximumOfferAmount(coupenDto.getMaximumOfferAmount());


        coupen.setCount(coupenDto.getCount());
        coupenRepository.save(coupen);
    }

    @Override
    public List<Coupen> findAll() {
        List<Coupen> coupens=coupenRepository.findAll();
        return coupens;
    }

    @Override
    public void enableCoupen(Long id) {
        Coupen coupen=coupenRepository.getReferenceById(id);
        coupen.setEnabled(true);
        coupenRepository.save(coupen);
    }

    @Override
    public void disableCoupen(Long id) {
        Coupen coupen=coupenRepository.getReferenceById(id);
        coupen.setEnabled(false);
        coupenRepository.save(coupen);
    }

    @Override
    public Coupen findByCoupenCode(String coupencode) {

        return coupenRepository.findByCoupenCode(coupencode);
    }

    @Override
    public void dicreseCoupen(long id) {
        Coupen coupen=coupenRepository.getReferenceById(id);
        coupen.setCount(coupen.getCount()-1);
        coupenRepository.save(coupen);
    }

    @Override
    public Coupen findByid(Long id) {
        return coupenRepository.getReferenceById(id);
    }
}
