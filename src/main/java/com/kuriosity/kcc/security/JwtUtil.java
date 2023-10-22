package com.kuriosity.kcc.security;

import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class JwtUtil {

    @Value("${jwt-secret}")
    private String jwtSecret;

    @Value("${jwt-expiration-ms}")
    private int jwtExpirationMs;

    public String generateToken(UserDetails userDetails) {
        Date now = new Date();
        Date expirationDate = new Date(now.getTime() + 3600000); // Token expiration time (1 hour)

        return Jwts.builder()
                .setSubject(userDetails.getUsername())
                .setIssuedAt(now)
                .setExpiration(expirationDate)
                .signWith(SignatureAlgorithm.HS256, jwtSecret)
                .compact();
    }

    public boolean validateToken(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    private String extractUsername(String token) {
        try {
            Claims claims = Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody();
            return claims.getSubject();
        } catch (ExpiredJwtException | SignatureException e) {

            return null;
        }
    }

    private boolean isTokenExpired(String token) {
        try {
            Claims claims = Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody();
            Date expirationDate = claims.getExpiration();
            return expirationDate.before(new Date());
        } catch (ExpiredJwtException | SignatureException e) {

            return true;
        }
    }

}


