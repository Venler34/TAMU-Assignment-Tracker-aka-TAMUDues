package com.venler42.tamu_dues_api.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.venler42.tamu_dues_api.model.CustomUserDetails;
import com.venler42.tamu_dues_api.service.MyUserDetailsService;

import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.springframework.security.core.Authentication;

/* For generating, parsing, and validating JWTs */

@Component
public class JWTUtil {

    // The secret key used to sign the JWT. This should be a secure key stored in
    // environment variables or configuration files.
    @Value("${spring.app.jwtSecret}")
    private String secretKey;

    // JWT expiration time (e.g., 1 hour)
    private final long expirationTime = 1000 * 60 * 60;

    private final MyUserDetailsService userDetailsService;

    public JWTUtil(MyUserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    // Create the Key object used for signing and validation
    private Key getSigningKey() {
        // Using HMAC SHA-256
        return new SecretKeySpec(secretKey.getBytes(), SignatureAlgorithm.HS256.getJcaName());
    }

    // Generate a JWT token
    public String generateToken(String username) {
        return Jwts.builder()
                .setSubject(username) // Set the username as the subject
                .setIssuedAt(new Date()) // Set the issue date
                .setExpiration(new Date(System.currentTimeMillis() + expirationTime)) // Set expiration date
                .signWith(getSigningKey()) // Use the signing key (SecretKey) to sign the JWT
                .compact();
    }

    // Validate the JWT token
    public boolean validateToken(String token) {
        try {
            // Parse the token and validate it using the signing key
            Jwts.parserBuilder()
                    .setSigningKey(getSigningKey()) // Use the signing key to validate
                    .build()
                    .parseClaimsJws(token); // Parse and validate the token
            return true;
        } catch (Exception e) {
            return false; // Invalid token or other parsing/validation issue
        }
    }

    // Extract the username from the JWT token
    public String extractUsername(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(getSigningKey()) // Use the signing key to parse and extract claims
                .build()
                .parseClaimsJws(token)
                .getBody(); // Get the body (claims) of the token
        return claims.getSubject(); // Extract and return the username (subject)
    }

    public String resolveToken(HttpServletRequest request) { // get jwt from header
        String bearerToken = request.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7); // Remove "Bearer " prefix
        }
        return null;
    }

    public UsernamePasswordAuthenticationToken getAuthentication(String token) {
        String username = extractUsername(token);
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);

        return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
    }

}
