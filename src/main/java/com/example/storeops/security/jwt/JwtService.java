package com.example.storeops.security.jwt;

import org.springframework.security.core.userdetails.UserDetails;

/**
 * The "JwtService" is responsible
 * for providing multiple methods for generating,
 * validating and extracting information from a
 * JWT token
 */
public interface JwtService {

    String generateToken(UserDetails userDetails);

    String extractUsername(String token);

    boolean isTokenValid(String token, UserDetails userDetails);

}
