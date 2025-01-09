package org.rag.security.service;

import io.jsonwebtoken.Claims;

public interface TokenService {

    String generate(Claims claims);

    Claims parseToken(String jwt);

    Integer getUserIdFromToken(String jwt);

    String getUserEmailFromToken(String jwt);
}
