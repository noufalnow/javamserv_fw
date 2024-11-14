package com.cboard.rental.tenants.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.cboard.rental.tenants.TenantsServiceApplication.TokenValidator;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class TenantsServiceSecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final TokenValidator tokenValidator; // Inject TokenValidator

    public TenantsServiceSecurityConfig(JwtAuthenticationFilter jwtAuthenticationFilter, TokenValidator tokenValidator) {
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
        this.tokenValidator = tokenValidator;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(requests -> requests
                        .requestMatchers("/v3/api-docs/**", "/swagger-ui.html", "/swagger-ui/**").permitAll() // Permit Swagger
                        .requestMatchers("/property/**").hasRole("ADMIN") // Protect property endpoints for ADMIN role
                        //.requestMatchers("/api/v1/acknowledgments").access("@tokenValidator.isTrustedService(request)") // Inter-service validation
                        .anyRequest().authenticated())
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class); // Add JWT filter

        return http.build();
    }
}
