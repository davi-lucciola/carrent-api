package io.api.carrent.domain.services;

import io.api.carrent.domain.dto.input.LoginDTO;
import io.api.carrent.domain.dto.output.AuthDTO;
import io.api.carrent.domain.entities.User;
import io.api.carrent.domain.ports.services.IAuthService;
import io.api.carrent.infra.jwt.JwtProprieties;
import io.api.carrent.infra.jwt.JwtService;
import io.api.carrent.infra.jwt.JwtUserDetailService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;

@Service
@RequiredArgsConstructor
public class AuthService implements IAuthService {
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
