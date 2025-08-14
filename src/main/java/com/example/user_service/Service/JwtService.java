package com.example.user_service.Service;

import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import java.security.Key;
import java.util.Date;

@Service
@RequiredArgsConstructor
public class JwtService {

    @Value("${jwt.secret}")
    private String secretBase64;

    @Value("${jwt.expiration:3600000}")
    private long expirationMs;

    @Value("${jwt.secret}")
    private String jwtSecret;

    private Key key() {
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecret.trim()));
    }


    public String generateToken(UserDetails user) {
        var now = new Date();
        var expiry = new Date(now.getTime() + expirationMs);
        return io.jsonwebtoken.Jwts.builder()
                .setSubject(user.getUsername())
                .claim("roles", user.getAuthorities().stream()
                        .map(GrantedAuthority::getAuthority).toList())
                .setIssuedAt(now)
                .setExpiration(expiry)
                .signWith(key(), io.jsonwebtoken.SignatureAlgorithm.HS256)
                .compact();
    }

    public String extractUsername(String token) {
        return io.jsonwebtoken.Jwts.parserBuilder()
                .setSigningKey(key()).build()
                .parseClaimsJws(token).getBody().getSubject();
    }

    public boolean isValid(String token, UserDetails user) {
        var username = extractUsername(token);
        var claims = io.jsonwebtoken.Jwts.parserBuilder()
                .setSigningKey(key()).build()
                .parseClaimsJws(token).getBody();
        return username.equals(user.getUsername()) && claims.getExpiration().after(new Date());
    }
    @PostConstruct
    public void checkSecret() {
        System.out.println("JWT Secret length: " + jwtSecret.length());
        System.out.println("JWT Secret (raw): '" + jwtSecret + "'");
    }

}
