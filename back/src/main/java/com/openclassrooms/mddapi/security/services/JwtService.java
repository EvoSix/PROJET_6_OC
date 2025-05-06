package com.openclassrooms.mddapi.security.services;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.security.Key;
import java.util.Date;


@Service
public class JwtService {
    /**
     * Secret key used for signing the JWT.
     * It should be kept secure and not hardcoded in production.
     */

    private final Key secretKey;

    public JwtService(@Value("${jwt.secret}") String secretKey) {
        this.secretKey = Keys.hmacShaKeyFor(secretKey.getBytes());
    }


    private final long validity = 365 * 24 * 60 * 60 * 1000;

    public String generateToken(String email) {
        return Jwts.builder()
                .subject(email)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + validity))
                .signWith(secretKey)
                .compact();

    }

    /**
     * Extracts the claims from a JWT token.
     *
     * @param token the JWT token
     * @return the claims from the JWT token
     * @throws JwtException if the token is invalid or cannot be parsed
     */
    public Claims extractClaims(String token) {
        return Jwts
                .parser()
                .verifyWith((SecretKey) secretKey)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    /**
     * Extracts the username from the JWT token.
     *
     * @param token the JWT token
     * @return the username stored in the token
     */
    public String extractUsername(String token) {
        return extractClaims(token).getSubject();
    }


    /**
     * Checks if the JWT token is expired.
     *
     * @param token the JWT token
     * @return true if the token is expired, false otherwise
     */
    public boolean isTokenExpired(String token) {
        return extractClaims(token).getExpiration().before(new Date());
    }

    /**
     * Validates the JWT token by checking if the username matches and if the token is not expired.
     *
     * @param token the JWT token
     * @param username the username to compare with the token's username
     * @return true if the token is valid, false otherwise
     */
    public boolean validateToken(String token, String username) {
        try {
            return (extractUsername(token).equals(username) && !isTokenExpired(token));
        } catch (JwtException e) {
            System.out.println("Token validation failed: " + e.getMessage());
            return false;
        }
    }
}
