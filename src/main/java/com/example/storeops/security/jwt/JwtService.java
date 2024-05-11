package com.example.storeops.security.jwt;

import org.springframework.security.core.userdetails.UserDetails;

/**
 * The "JwtService" is responsible
 * for providing multiple methods for generating,
 * validating and extracting information from a
 * JWT token
 */
public interface JwtService {

    // Generates a JWT
    String generateToken(UserDetails userDetails);

    // Extracts the username (email) from the JWT
    String extractUsername(String token);

    // Verifies whether a JWT is valid
    boolean isTokenValid(String token, UserDetails userDetails);

}
