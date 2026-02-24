package com.example.ecommerce.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.example.ecommerce.jwt.AuthJwtFilter;
import com.example.ecommerce.jwt.UnAuthorizedErrorHandler;
import com.example.ecommerce.service.ConfigService.CustomizedUserDetailsService;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    @Autowired
    CustomizedUserDetailsService customizedUserDetailsService;

    @Autowired
    UnAuthorizedErrorHandler unAuthorizedErrorHandler;

    @Bean
    AuthJwtFilter authJwtFilter(){
        return new AuthJwtFilter();
    }


    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity){
        httpSecurity.csrf(csrf->csrf.disable());
        httpSecurity.authorizeHttpRequests(httpRequests->httpRequests
        .requestMatchers("/api/register","/api/login")
        .permitAll()
        .anyRequest()
        .authenticated()); 
        httpSecurity.exceptionHandling(exepection->exepection.authenticationEntryPoint(this.unAuthorizedErrorHandler));
        httpSecurity.addFilterBefore(this.authJwtFilter(), UsernamePasswordAuthenticationFilter.class);
        httpSecurity.sessionManagement(session->session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        return httpSecurity.build();
    }

    @Bean
    AuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider provider=new DaoAuthenticationProvider(this.customizedUserDetailsService);
        provider.setPasswordEncoder(this.passwordEncoder());
        return provider;
    }

    @Bean
    PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    AuthenticationManager authenticationManager(AuthenticationConfiguration configuration){
        return configuration.getAuthenticationManager();
    }

    
}
