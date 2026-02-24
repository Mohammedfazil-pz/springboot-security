package com.example.ecommerce.jwt;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.print.attribute.standard.Media;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import tools.jackson.databind.ObjectMapper;

@Component
public class UnAuthorizedErrorHandler implements AuthenticationEntryPoint{

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
            AuthenticationException authException) throws IOException, ServletException {
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

        Map<String,Object> errorObject=new HashMap<>();

        errorObject.put("message", authException.getMessage());
        errorObject.put("status",false);
        System.out.println(authException);

        final ObjectMapper JSON_CONVERTER=new ObjectMapper();
        JSON_CONVERTER.writeValue(response.getOutputStream(), errorObject);
    }

}
