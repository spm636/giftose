package com.ecommerce.library.model;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;

import java.time.LocalDate;
import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Data
@ToString
@Entity
@Table(name="coupen")
public class Coupen {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "coupen_id")
    private Long id;

    private String coupencode;
    private String coupenDescription;
    private String offerPercentage;
    @DateTimeFormat(pattern = "yyyy/mm/dd")
    private LocalDate startDate;
    @DateTimeFormat(pattern = "yyyy/mm/dd")
    private LocalDate expireDate;
    private Double minimumOrderAmount;
    private Double maximumOfferAmount;
    private int count;
    private boolean enabled;
    public boolean isExpired(){
        return (this.count == 0 || this.expireDate.isBefore(LocalDate.now()) || this.startDate.isAfter(LocalDate.now()));
    }

}
