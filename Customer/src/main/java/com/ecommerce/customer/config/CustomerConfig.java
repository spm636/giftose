package com.ecommerce.customer.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class CustomerConfig {
    @Autowired
    CustomSuccessHandler customSuccessHandler;
    @Bean
    public PasswordEncoder passwordEncoder(){
        return NoOpPasswordEncoder.getInstance();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{

        http.authorizeHttpRequests(configurer->
                configurer

                        .requestMatchers("/**","/about","/contact","/productDetails").permitAll()
                        .requestMatchers("/css/**","/fonts/**","/images/**","/js/**","/login/**","/signup/**").permitAll()

                        .anyRequest().authenticated()



        )

                .formLogin(form->
                        form
                                .loginPage("/loginPage")
                                .loginProcessingUrl("/authenticateTheUser")
                                .successHandler(customSuccessHandler)
                                .permitAll()

                )
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.ALWAYS)
                        .invalidSessionUrl("/")
                        .maximumSessions(1)
                        .maxSessionsPreventsLogin(false)



                )


                .logout(LogoutConfigurer->
                        LogoutConfigurer
                                .logoutSuccessUrl("/loginPage")

                )
                .csrf(AbstractHttpConfigurer::disable);



        return http.build();
    }
}
