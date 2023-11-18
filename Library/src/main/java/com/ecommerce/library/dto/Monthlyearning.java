package com.ecommerce.library.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
public class Monthlyearning {
  private Date month;
   private Double earning;
   private Long totelOrder;
   private Long delivered_orders;
   private Long cancelled_orders;

    public Monthlyearning(Date month, Double earning,Long totelOrder,Long delivered_orders,Long cancelled_orders) {
        this.month = month;
        this.earning = earning;
        this.totelOrder=totelOrder;

        this.delivered_orders=delivered_orders;
        this.cancelled_orders=cancelled_orders;
    }
}
