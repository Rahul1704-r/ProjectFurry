package com.furryhub.gateway.security;

import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.security.core.context.SecurityContext;


@Configuration
public class PrincipalHeaderFilterConfig {

    @Bean
    public GlobalFilter addUserEmailHeaderFilter() {
        return (exchange, chain) -> ReactiveSecurityContextHolder.getContext()
                .map(SecurityContext::getAuthentication)
                .flatMap(auth -> {
                    if (auth != null && auth.isAuthenticated()) {
                        String email = auth.getName(); // subject from JWT
                        var mutatedRequest = exchange.getRequest().mutate()
                                .header("X-User-Email", email)
                                .build();
                        var mutatedExchange = exchange.mutate().request(mutatedRequest).build();
                        return chain.filter(mutatedExchange);
                    }
                    return chain.filter(exchange);
                })
                .switchIfEmpty(chain.filter(exchange));
    }
}