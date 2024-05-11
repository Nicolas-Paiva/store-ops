package com.example.storeops.security.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.function.Function;

@Service
public class JwtServiceImpl implements JwtService{

    private final long expirationTime = 3600000; // 1 day
    private final String SECRET_KEY = "d300eda36ee988fb5adb0eb1de9515081696f1ced93342c895883dd207380904";

    /**
     * Generates a JWT token based on
     * the userDetails provided
     * @param userDetails the userDetails (the user)
     * @return a JWT token
     */
    public String generateToken(UserDetails userDetails){
        return Jwts.builder()
                .subject(userDetails.getUsername())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + 1000 * 60))
                .signWith(getSigningKey())
                .compact();

    }

    /**
     * Provides a "SecretKey" for signing
     * a JWT token, preventing external
     * users from creating false tokens.
     *
     * @return a SecretKey for signing
     * a JWT token
     */
    private SecretKey getSigningKey(){
        byte[] key = Decoders.BASE64URL.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(key);
    }


    /**
     * Extracts all the claims from a JWT token
     *
     * @param token a JWT token
     *
     * @return all the claims from a JWT token
     */
    private Claims extractAllClaims(String token) {
        return Jwts.parser().verifyWith(getSigningKey())
                .build().parseSignedClaims(token).getPayload();

    }

    /**
     * Extracts a specific claim from a JWT token.
     *
     * @param token the JWT token
     *
     * @param claimsResolver a method from the
     * Claims class, which can, for example,
     * retrieve the username.
     *
     * @return A specific claim from the JWT token
     */
    private <T> T extractClaim(String token, Function<Claims, T> claimsResolver){
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    /**
     * Extracts the subject (which in our case is the email)
     * from a JWT token
     *
     * @param token a JWT token
     *
     * @return the user's email
     */
    public String extractUsername(String token){
        return extractClaim(token, Claims::getSubject);
    }

    /**
     * Checks whether the expiration date
     * of the token was before or after
     * the current time
     *
     * @param token a JWT token
     *
     * @return a boolean that indicates
     * whether the token is expired or not
     */
    private boolean isTokenExpired(String token){
        return extractClaim(token, Claims::getExpiration)
                .before(new Date());
    }

    /**
     * Verifies whether a token is valid,
     * checking for the actual username in the token,
     * as well as the expiration time.
     *
     * @param token a JWT token
     *
     * @param userDetails the UserDetails which provides
     * methods such as the user's username (in our case, email)
     *
     * @return a boolean that indicates whether the
     * token is valid.
     */
    public boolean isTokenValid(String token, UserDetails userDetails){
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

}
