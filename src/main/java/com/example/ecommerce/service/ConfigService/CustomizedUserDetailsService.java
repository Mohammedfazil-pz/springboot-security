package com.example.ecommerce.service.ConfigService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.ecommerce.model.User;
import com.example.ecommerce.repo.UserRepository;

@Service
public class CustomizedUserDetailsService implements UserDetailsService{

    @Autowired
    UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User isExists=this.userRepository.findByUsername(username);
        if(isExists!=null){
            return org.springframework.security.core.userdetails.User.builder().username(isExists.getUsername()).password(isExists.getPassword()).roles(isExists.getRole()).build();
        }
        return null;
    }

    

}
