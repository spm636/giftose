package com.ecommerce.admin.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;
import java.util.List;

@Getter
@Setter
public class CustomAdmin extends User {
    private int id;
private String name;
private String email;

    public CustomAdmin(String username, String password, Collection<? extends GrantedAuthority> authorities, int id, String name, String email) {
        super(username, password, authorities);
        this.id = id;
        this.name = name;
        this.email = email;
    }
}
