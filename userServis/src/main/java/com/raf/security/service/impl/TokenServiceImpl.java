package com.raf.security.service.impl;
import com.raf.security.service.TokenService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;

import javax.crypto.SecretKey;

@Service
public class TokenServiceImpl implements TokenService {

    @Value("${oauth.jwt.secret}")
    private String jwtSecret;

    private SecretKey getSecretKey() {
        // Convert the string-based jwtSecret to a SecretKey
        return Keys.hmacShaKeyFor(jwtSecret.getBytes());
    }

    @Override
    public String generate(Claims claims) {
        // Use the same key derived from jwtSecret for signing the token
        SecretKey secretKey = getSecretKey();
        return Jwts.builder()
                .setClaims(claims)
                .signWith(secretKey, SignatureAlgorithm.HS512)
                .compact();
    }

    @Override
    public Claims parseToken(String jwt) {
        Claims claims;
        try {
            // Use the same key derived from jwtSecret for parsing the token
            SecretKey secretKey = getSecretKey();
            claims = Jwts.parserBuilder()
                    .setSigningKey(secretKey)
                    .build()
                    .parseClaimsJws(jwt)
                    .getBody();
        } catch (JwtException e) {
            System.out.println("Token parsing failed: " + e.getMessage());
            return null; // Return null in case of invalid token
        }
        return claims;
    }
}