package com.example.ecommerce.service.ControllerServices;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.ecommerce.jwt.JwtUtils;
import com.example.ecommerce.model.User;
import com.example.ecommerce.repo.UserRepository;
import com.example.ecommerce.service.ConfigService.CustomizedUserDetailsService;

@Service
public class UserService {
    @Autowired
    UserRepository userRepository;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    JwtUtils jwtUtils;

    @Autowired
    CustomizedUserDetailsService customizedUserDetailsService;

    public ResponseEntity<?> registerUser(User user){
        try {
              user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
              this.userRepository.save(user);
              Map<String,Object> responseObject=new HashMap<>();
              responseObject.put("message", "User Created Successfully.");
              responseObject.put("status", true);
              return new ResponseEntity<>(responseObject,HttpStatus.CREATED);
        } catch (Exception e) {
              Map<String,Object> errorObject=new HashMap<>();
              errorObject.put("message", e.getMessage());
              errorObject.put("status", false);
              return new ResponseEntity<>(errorObject,HttpStatus.INTERNAL_SERVER_ERROR);
        }        
    }

    public ResponseEntity<?> loginUser(User user){
        Authentication authentication;
        try {
            authentication= this.authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));

             String token=this.jwtUtils.generateTokenByUsername(user.getUsername());
             SecurityContextHolder.getContext().setAuthentication(authentication);
             
              Map<String,Object> responseObject=new HashMap<>();
              responseObject.put("message", "Welcome Back "+ user.getUsername());
              responseObject.put("token", token);
              responseObject.put("status", true);
              return new ResponseEntity<>(responseObject,HttpStatus.OK);

            
        } catch (Exception e) {
              Map<String,Object> errorObject=new HashMap<>();
              errorObject.put("message", e.getMessage());
              errorObject.put("status", false);
              return new ResponseEntity<>(errorObject,HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public List<User> fetchAllUsers() {
       return this.userRepository.findByRole("USER");
    }

}
