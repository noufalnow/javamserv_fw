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

    // Extract Username
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    // Extract Claim
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    // Validate Token
    public boolean validateToken(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    // Check if Token is Expired
    /*private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }*/

    // Extract Expiration Date
    /*public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }*/

    // Extract All Claims
    private Claims extractAllClaims(String token) {
        try {
            return Jwts.parser()
                       .setSigningKey(SECRET_KEY.getBytes())
                       .parseClaimsJws(token)
                       .getBody();
        } catch (Exception e) {
            // Log or handle exception as needed
            throw new IllegalArgumentException("Invalid JWT Token", e);
        }
    }

    public UserDetails extractUserDetails(String token) {
        String username = extractUsername(token);
        List<String> roles = extractClaim(token, claims -> claims.get("roles", List.class));

        return User.builder()
                .username(username)
                .password("") // Password is not stored in JWT
                .roles(roles.stream()
                    .map(role -> role.replace("ROLE_", "")) // Ensure roles match Spring format
                    .toArray(String[]::new)) // Convert list to array
                .build();
    }

    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration); // This will now use extractAllClaims
    }

    public boolean isTokenExpired(String token) {
        Date expiration = extractClaim(token, Claims::getExpiration);
        // If expiration is null, assume the token does not expire
        return expiration != null && expiration.before(new Date());
    }
}
