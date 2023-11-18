package com.ecommerce.library.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class CustomerDto {
    private Long customer_id;
    @NotNull(message = "enter the name")
    @Size(max=20,message = " name shoud maximum 20 letter")
    private String name;
    @NotNull(message = "enter the email")
    private String email;

    private String password;
    private String repeatPassword;


    private Long mobile;
    private String role;
    private boolean activated;
    private boolean blocked;
    private long otp;



}
