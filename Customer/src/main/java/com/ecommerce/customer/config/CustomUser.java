package com.ecommerce.customer.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;
@Getter
@Setter
public class CustomUser extends User {
    private String name;
    private Long customer_id;
    private Long mobile;

    public CustomUser(String username, String password, Collection<? extends GrantedAuthority> authorities, String name, Long customer_id, Long mobile) {
        super(username, password, authorities);
        this.name = name;
        this.customer_id=customer_id;
        this.mobile = mobile;
    }
}
