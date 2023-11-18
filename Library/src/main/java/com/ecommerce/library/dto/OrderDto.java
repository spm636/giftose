package com.ecommerce.library.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;


@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
public class OrderDto {
    private String pdfReport;
    private String csvReport;
    private String orderStatus;


}
