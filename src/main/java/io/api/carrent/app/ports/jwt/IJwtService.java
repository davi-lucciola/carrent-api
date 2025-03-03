package io.api.carrent.app.ports.jwt;

import org.springframework.security.core.userdetails.UserDetails;

public interface IJwtService {
    String generateAccessToken(UserDetails user);
}
