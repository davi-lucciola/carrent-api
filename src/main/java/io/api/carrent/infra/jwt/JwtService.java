package io.api.carrent.infra.jwt;

import io.api.carrent.core.ports.jwt.IJwtService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.hibernate.mapping.Any;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class JwtService implements IJwtService {
    private final SecretKey secretKey;
    private final JwtProprieties jwtProprieties;

    public JwtService(JwtProprieties jwtProprieties) {
        this.secretKey = Keys.hmacShaKeyFor(jwtProprieties.getKey().getBytes());
        this.jwtProprieties = jwtProprieties;
    }

    public String generate(
            UserDetails user,
            Date expirationDate,
            Map<String, Any> claims
    ) {
        return Jwts.builder()
                .claims()
                .subject(user.getUsername())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(expirationDate)
                .add(claims)
                .and()
                .signWith(secretKey)
                .compact();
    }

    private Claims getAllClaims(String token) {
        var parser = Jwts.parser().verifyWith(secretKey).build();
        return parser.parseSignedClaims(token).getPayload();
    }

    public String extractEmail(String token) {
        return this.getAllClaims(token).getSubject();
    }

    public boolean isExpired(String token) {
        return this.getAllClaims(token)
                .getExpiration()
                .before(new Date(System.currentTimeMillis()));
    }

    public boolean isValid(String token, UserDetails user) {
        String email = this.extractEmail(token);
        return user.getUsername().equals(email);
    }

    public String generateAccessToken(UserDetails user) {
        var tokenExpiration = new Date(System.currentTimeMillis() + jwtProprieties.getAccessTokenExpiration());
        return this.generate(user, tokenExpiration, new HashMap<>());
    }
}
