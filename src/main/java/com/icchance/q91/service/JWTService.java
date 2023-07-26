package com.icchance.q91.service;

import com.icchance.q91.entity.model.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.security.Key;

@Service
public class JWTService {

    private AuthenticationManager authenticationManager;

    private final String KEY = "";

    public String generateToken(User user) {

        Authentication authentication = new UsernamePasswordAuthenticationToken(user.getAccount(), user.getPassword());
        authentication = authenticationManager.authenticate(authentication);
        //User user = (User) authentication.getPrincipal();
        Claims claims = Jwts.claims();
        //claims.put("user_id", )
        Key secretKey = Keys.hmacShaKeyFor(KEY.getBytes());

        return Jwts.builder().setClaims(claims).signWith(secretKey).compact();
    }
}
