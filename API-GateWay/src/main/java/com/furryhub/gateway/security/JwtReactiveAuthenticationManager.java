package com.furryhub.gateway.security;


import io.jsonwebtoken.Claims;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.List;

@Component
public class JwtReactiveAuthenticationManager implements ReactiveAuthenticationManager {

    private static final Logger log = LoggerFactory.getLogger(JwtReactiveAuthenticationManager.class);
    private final JwtUtil jwtUtil;

    public JwtReactiveAuthenticationManager(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Override
    public Mono<Authentication> authenticate(Authentication authentication) {
        String token = authentication.getCredentials().toString();
        try {
            Claims claims = jwtUtil.validateToken(token);
            String username = claims.getSubject();
            return Mono.just(new UsernamePasswordAuthenticationToken(username, null, List.of()));
        } catch (Exception e) {
            log.warn("[Gateway] JWT validation failed: {}", e.getMessage());
            return Mono.empty();
        }
    }
}
