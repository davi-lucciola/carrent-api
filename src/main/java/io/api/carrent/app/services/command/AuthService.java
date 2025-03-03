package io.api.carrent.app.services.command;

import io.api.carrent.app.ports.jwt.IJwtService;
import io.api.carrent.domain.dto.input.LoginDTO;
import io.api.carrent.domain.dto.output.AuthDTO;
import io.api.carrent.domain.services.command.IAuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;



@Service
@RequiredArgsConstructor
public class AuthService implements IAuthService {
    private final IJwtService jwtService;
    private final AuthenticationManager authManager;
    private final UserDetailsService userDetailsService;

    public AuthDTO login(LoginDTO loginDTO) {
        UserDetails user = userDetailsService.loadUserByUsername(loginDTO.email());

        var accessToken = jwtService.generateAccessToken(user);

        var authToken = new UsernamePasswordAuthenticationToken(loginDTO.email(), loginDTO.password());
        authManager.authenticate(authToken);

        return new AuthDTO(accessToken, "bearer");
    }
}
