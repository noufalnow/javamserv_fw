package com.cboard.rental.gateway.config;

import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Configuration
public class GlobalFilterConfig {

    @Bean
    public GlobalFilter customGlobalFilter() {
        return (exchange, chain) -> {
            String redisKey = generateRedisKey(exchange);
            System.out.println("Generated Redis Key: " + redisKey);

            // Pre-processing logic using the Redis key
            // Perform operations such as checking rate limits here

            return chain.filter(exchange).then(Mono.fromRunnable(() -> {
                // Post-processing logic, if any
                System.out.println("Completed processing for Redis Key: " + redisKey);
            }));
        };
    }

    private String generateRedisKey(ServerWebExchange exchange) {
        // Obtain the client IP address
        String clientIp = exchange.getRequest().getRemoteAddress().getAddress().getHostAddress();
        
        // Obtain the request path
        String requestPath = exchange.getRequest().getURI().getPath();

        // Construct the Redis key with a cleaned client IP and request path
        return "rate_limit:" + clientIp + ":" + requestPath;
    }
}

