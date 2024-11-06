package com.cboard.rental.tenants.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.function.Function;

@Service
public class JWTService {

    @Value("${jwt.secret}")
    private String SECRET_KEY;

    @Value("${jwt.trusted.issuer}")
    private String TRUSTED_ISSUER; // Configure this as the issuer for inter-service tokens

    // Extract Username
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    // Extract Claim
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    // Validate Token for a Specific User
    public boolean validateToken(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    // Check if Token is Expired
    public boolean isTokenExpired(String token) {
        Date expiration = extractClaim(token, Claims::getExpiration);
        return expiration != null && expiration.before(new Date());
    }

    // Extract All Claims
    private Claims extractAllClaims(String token) {
        try {
            return Jwts.parser()
                       .setSigningKey(SECRET_KEY.getBytes())
                       .parseClaimsJws(token)
                       .getBody();
        } catch (Exception e) {
            throw new IllegalArgumentException("Invalid JWT Token", e);
        }
    }

    // Method to validate token for inter-service communication
    public boolean isTrustedServiceToken(String token) {
        Claims claims = extractAllClaims(token);
        String issuer = claims.getIssuer();
        return TRUSTED_ISSUER.equals(issuer) && !isTokenExpired(token); // Valid if issuer matches and token is not expired
    }

    public UserDetails extractUserDetails(String token) {
        String username = extractUsername(token);
        List<String> roles = extractClaim(token, claims -> claims.get("roles", List.class));

        return User.builder()
                .username(username)
                .password("") // Password is not stored in JWT
                .roles(roles.stream()
                    .map(role -> role.replace("ROLE_", ""))
                    .toArray(String[]::new))
                .build();
    }
}
