package com.example.chatappback.service;

import com.example.chatappback.entity.User;
import com.example.chatappback.repository.UserRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;

@Configuration
public class JwtService {
    @Autowired
    private UserService userService;
    String secret = "SECjfkdsjkfjdksjkjjfsdjfj3kljkl4j32kjkj34k2j4j32k4jkdfgjkajfksdjfkljsdkRET";
    public String generateToken(User userDetails) {
        HashMap<String, Object> claims = new HashMap();
        claims.put("username", userDetails.getUsername());
        claims.put("id", userDetails.getId());
        return Jwts.builder()
                .subject(userDetails.getUsername())
                .signWith(SignatureAlgorithm.HS256, secret)
                .claims(claims)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + 600000))
                .compact();
    }
    public String getUsername(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(getSigningKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
        return claims.getSubject();
    }
    public String extractId(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(getSigningKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
        return claims.get("id", String.class);
    }
    public boolean isTokenExpired(String token) {
        Date expirationDate = Jwts.parser()
                .setSigningKey(getSigningKey())
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getExpiration();
        return expirationDate.before(new Date());
    }
    private Key getSigningKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secret);
        return Keys.hmacShaKeyFor(keyBytes);
    }
    public UserDetails loadUserByToken(String token) {
        String username = getUsername(token);
        User user = userService.findUserByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        return user;
    }
}
