package com.example.ecommerce.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.ecommerce.model.User;
import com.example.ecommerce.service.ControllerServices.UserService;

@RestController
@RequestMapping("/api")
public class AuthController {

    @Autowired
    UserService userService;


    @RequestMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody User user){
        return this.userService.registerUser(user);
    }

    @RequestMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody User user){
        return this.userService.loginUser(user);
    }
}
