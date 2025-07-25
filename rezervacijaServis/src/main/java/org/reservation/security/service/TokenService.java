package org.reservation.security.service;

import io.jsonwebtoken.Claims;

public interface TokenService {

    String generate(Claims claims);

    Claims parseToken(String jwt);
    Integer getUserIdFromToken(String jwt);
    String getUserRoleFromToken(String jwt);
}
