package com.payroll.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.payroll.service.UserDetailsServiceImpl;

@Configuration
public class SecurityConfig {
    // A bean that provides the user details service for authentication
    @Bean
    public UserDetailsService getDetailsService() {
        return new UserDetailsServiceImpl();
    }

    // A bean that provides the password encoder for encoding and verifying passwords
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // A bean that provides the authentication provider that uses the user details service and the password encoder
    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider daoAuthenticationProvider=new DaoAuthenticationProvider();
        daoAuthenticationProvider.setUserDetailsService(getDetailsService());
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
        return daoAuthenticationProvider;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.authorizeRequests()
            .requestMatchers("/admin/**").hasRole("ADMIN")
            .requestMatchers("/user/**").hasRole("USER")
            .requestMatchers("/**").permitAll()
            .and()
            .formLogin()
                .loginPage("/login")
                .defaultSuccessUrl("/dashboard") // Specify the URL to redirect after successful login
                .and()
            .csrf().disable();

        return http.build();
    }




    // A bean that provides the web security customizer that configures the global security settings, such as ignoring certain requests or adding debug filters
    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> {
            // add your customizations here
        };
    }

}
