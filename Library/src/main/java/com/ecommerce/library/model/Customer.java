package com.ecommerce.library.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
@Entity
@Table(name = "customer",uniqueConstraints = @UniqueConstraint(columnNames = "email"))
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long customer_id;

    private String name;
    private String email;


    private Long mobile;
    private String password;
    private String role;

    private static final long OTP_VALID_DURATION = 5 * 60 * 1000;
    @Column(name = "one_time_password")
    private String otp;

    @Column(name = "otp_requested_time")
    private LocalDateTime otpRequestedTime;
    @Column(name = "is_activated")
    private boolean activated;
    @Column(name = "is_blocked")
    private boolean blocked;

  //@ToString.Exclude
  //@OneToMany(mappedBy = "customer", cascade = CascadeType.ALL)
   //private List<ShopingCart> cart;



    @ToString.Exclude
    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL)
    private List<Address> address;

    @Column(name = "reset_password_token")
    private String resetPasswordToken;

    @Column(name="referalToke")
    private String referalToken;




}
