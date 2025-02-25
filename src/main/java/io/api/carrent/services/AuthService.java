package io.api.carrent.services;

import io.api.carrent.dto.input.LoginDTO;
import io.api.carrent.dto.output.AuthDTO;
import io.api.carrent.entities.User;
import io.api.carrent.security.jwt.JwtProprieties;
import io.api.carrent.security.jwt.JwtService;
import io.api.carrent.security.jwt.JwtUserDetailService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final JwtService jwtService;
    private final JwtProprieties jwtProprieties;
    private final AuthenticationManager authManager;
    private final JwtUserDetailService jwtUserDetailService;

    public AuthDTO login(LoginDTO loginDTO) {
        User user = jwtUserDetailService.loadUserByUsername(loginDTO.email());

        var accessToken = generateAccessToken(user);

        var authToken = new UsernamePasswordAuthenticationToken(loginDTO.email(), loginDTO.password());
        authManager.authenticate(authToken);

        return new AuthDTO(accessToken, "bearer");
    }

    private String generateAccessToken(User user) {
        var tokenExpiration = new Date(System.currentTimeMillis() + jwtProprieties.getAccessTokenExpiration());
        return jwtService.generate(user, tokenExpiration, new HashMap<>());
    }
}
