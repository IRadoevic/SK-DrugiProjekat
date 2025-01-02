package org.rag.security.service.impl;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.rag.security.service.TokenService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;

@Service
public class TokenServiceImpl implements TokenService {

    @Value("${oauth.jwt.secret}")
    private String jwtSecret;

    private SecretKey getSecretKey() {
        return Keys.hmacShaKeyFor(jwtSecret.getBytes());
    }

    @Override
    public String generate(Claims claims) {
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
            SecretKey secretKey = getSecretKey();
            claims = Jwts.parserBuilder()
                    .setSigningKey(secretKey)
                    .build()
                    .parseClaimsJws(jwt)
                    .getBody();
        } catch (JwtException e) {
            System.out.println("Token parsing failed: " + e.getMessage());
            return null;
        }
        return claims;
    }
}
