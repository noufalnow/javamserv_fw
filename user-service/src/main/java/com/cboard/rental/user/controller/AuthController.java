package com.cboard.rental.user.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import com.cboard.rental.user.config.JWTService;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JWTService jwtService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestParam String username, @RequestParam String password) {
        try {
            // Authenticate user
            Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(username, password)
            );

            // Generate JWT token if authentication is successful
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            String token = jwtService.generateToken(userDetails);

            // Return token in JSON format
            Map<String, String> response = new HashMap<>();
            response.put("token", "Bearer " + token);

            return ResponseEntity.ok(response);
        } catch (AuthenticationException ex) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
        }
    }
}

/*
 * 
 * 
curl -X POST http://localhost:8091/auth/login      -d "username=admin"      -d "password=adminPassword"      -H "Content-Type: application/x-www-form-urlencoded"
{"token":"Bearer eyJhbGciOiJIUzI1NiJ9.eyJyb2xlIjoiUk9MRV9BRE1JTiIsInN1YiI6ImFkbWluIiwiaWF0IjoxNzMwMTA2MzkyLCJleHAiOjE3MzAxOTI3OTJ9.AOGG8n8N2wCi4Qx699Y3JMnbA2oPIySNzXKSK1u4R2Q"}

curl -H "Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJyb2xlcyI6WyJST0xFX0FETUlOIl0sInN1YiI6ImFkbWluIiwiaWF0IjoxNzMwMTA3OTQzLCJleHAiOjE3MzAxOTQzNDN9.P3_-UDB9NYeTs4X2-DEnonQtGZUERb5QOiiQr083tQk" http://localhost:8080/users/admin-endpoint

 * 
 * */
