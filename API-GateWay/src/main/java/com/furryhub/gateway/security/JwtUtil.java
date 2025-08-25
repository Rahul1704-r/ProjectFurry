package com.furryhub.gateway.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.stereotype.Component;

@Component
public class JwtUtil {

    private static final String SECRET = "nvjqhvjeqiovh89yuvh8onc89n1234567890123456"; // Same secret used in Auth Service

    public Claims validateToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(SECRET.getBytes())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}
