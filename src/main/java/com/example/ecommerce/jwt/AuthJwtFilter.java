package com.example.ecommerce.jwt;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class AuthJwtFilter extends OncePerRequestFilter{

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    JwtUtils jwtUtils;

    @Autowired
    UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        try {
            String token=this.jwtUtils.extractTokenFromHeader(request);
            if(token!=null&&this.jwtUtils.validateToken(token)){
                String username=this.jwtUtils.extractUsernameFromToken(token);
                UserDetails userDetails=this.userDetailsService.loadUserByUsername(username);

                Authentication LoggedInUserObectCreationForSecurityHolder=new UsernamePasswordAuthenticationToken(userDetails.getUsername(), null,userDetails.getAuthorities());
            
                SecurityContextHolder.getContext().setAuthentication(LoggedInUserObectCreationForSecurityHolder);
            }
        } catch (Exception e) {
           System.out.println(e);
           throw new SecurityException(e.getMessage());
        }

        filterChain.doFilter(request, response);
    }

}
