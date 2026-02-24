package com.example.ecommerce.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.ecommerce.model.User;
import com.example.ecommerce.service.ControllerServices.UserService;

@RestController
@RequestMapping("/api")
public class HomeController {

    @Autowired
    UserService userService;

    @GetMapping("/")
    public String greetings(){
        return "Welcome back. I am Mohammed Fazil!";
    }

    @GetMapping("/users")
    @PreAuthorize("hasRole('ADMIN')")
    public List<User> fetchAllUsers(){
        return this.userService.fetchAllUsers();
    }

}
