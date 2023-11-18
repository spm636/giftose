package com.ecommerce.library.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.NotFound;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AdminDto {
    private int id;
@NotNull(message = "enter name")
    private String name;
    @NotNull(message = "enter email")
    private String email;
    @Size(min = 4)
    private String password;
    @NotNull(message = "enter username")
    private String username;
    @NotNull(message = "enter role")
    private String role;
}
