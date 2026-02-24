package com.example.ecommerce.jwt;


import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;

@Component
public class JwtUtils {

    @Value("${spring.jwt.secret.key}")
    private String jwtSecretKey;

    public String extractTokenFromHeader(HttpServletRequest http){
        String authorization=http.getHeader("Authorization");
        if(authorization!=null&&authorization.startsWith("Bearer")){
            return authorization.substring(7);
        }
        return null;
    }

    public String generateTokenByUsername(String username){
        return Jwts.
        builder()
        .subject(username)
        .issuedAt(new Date())
        .expiration(new Date(System.currentTimeMillis()+1000*60*60*7))
        .signWith(this.key())
        .compact();
    }

    public SecretKey key(){
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(this.jwtSecretKey));
    }

    public String extractUsernameFromToken(String token){
        return Jwts.parser().verifyWith(this.key()).build().parseSignedClaims(token).getPayload().getSubject();
    }

    public Boolean validateToken(String token){
        try {
            Jwts.parser().verifyWith(this.key()).build().parseSignedClaims(token);
            return true;
        } catch (Exception e) {
            System.out.println(e);
            return false;
        }
    }

}
