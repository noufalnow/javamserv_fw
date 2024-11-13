package com.cboard.rental.payments.clients;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import jakarta.servlet.http.HttpServletRequest;

@Configuration
public class FeignConfig {

    @Bean
    public RequestInterceptor requestInterceptor() {
        return requestTemplate -> {
            try {
                // Retrieve the current HTTP request
                ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
                if (attributes != null) {
                    HttpServletRequest request = attributes.getRequest();
                    String authHeader = request.getHeader("Authorization");

                    // Add the token to the outgoing Feign request if it exists
                    if (authHeader != null) {
                        requestTemplate.header("Authorization", authHeader);
                    }
                }
            } catch (Exception e) {
                // Log the exception to understand what went wrong, if any
                System.err.println("Failed to set Authorization header in Feign request: " + e.getMessage());
            }
        };
    }
}
