package com.raf.security;

import com.raf.repository.UserRepository;
import com.raf.security.service.TokenService;
import io.jsonwebtoken.Claims;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class BanAspect {
    private final TokenService tokenService;
    private final UserRepository userRepository;

    public BanAspect(TokenService tokenService, UserRepository userRepository) {
        this.tokenService = tokenService;
        this.userRepository = userRepository;
    }

    @Around("@annotation(com.raf.security.CheckBan)")
    public Object checkBan(ProceedingJoinPoint joinPoint) throws Throwable {
        // Preuzmi JWT token iz parametara metode
        String token = null;
        Object[] args = joinPoint.getArgs();
        for (Object arg : args) {
            if (arg instanceof String && ((String) arg).startsWith("Bearer ")) {
                token = ((String) arg).substring(7); // Ukloni "Bearer "
                break;
            }
        }

        // Ako nema tokena, vrati UNAUTHORIZED
        if (token == null) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        // Parsiraj token da dobiješ ID korisnika
        Claims claims = tokenService.parseToken(token);
        if (claims == null) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        Long userId = claims.get("id", Long.class);
        // Proveri status banovanja korisnika
        boolean isBanned = userRepository.findById(userId)
                .map(user -> user.getBanned())
                .orElse(true); // Ako korisnik ne postoji, tretira se kao banovan

        if (isBanned) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
        // Ako nije banovan, nastavi sa izvršavanjem metode
        return joinPoint.proceed();
    }
}
