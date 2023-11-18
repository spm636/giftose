package com.ecommerce.library.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor

public class DailyEarning {
    private Date date;

    private double earnings;

    private Long totelOrder;

    public DailyEarning(Date date, double earnings,Long totelOrder) {
        this.date = date;
        this.earnings = earnings;
        this.totelOrder=totelOrder;

    }
}
