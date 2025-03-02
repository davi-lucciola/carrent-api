package io.api.carrent.services.commands;

import io.api.carrent.core.services.command.AuthService;
import io.api.carrent.domain.dto.input.LoginDTO;
import io.api.carrent.domain.dto.output.AuthDTO;
import io.api.carrent.domain.entities.User;
import io.api.carrent.infra.jwt.JwtProprieties;
import io.api.carrent.infra.jwt.JwtService;
import io.api.carrent.infra.jwt.JwtUserDetailService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Date;

import static org.mockito.Mockito.mock;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest {

    @Mock
    private JwtService jwtService;
    @Mock
    private JwtProprieties jwtProprieties;
    @Mock
    private AuthenticationManager authManager;
    @Mock
    private JwtUserDetailService jwtUserDetailService;

    @InjectMocks
    private AuthService authService;

    @Test
    void shouldLoginSuccessfully() {
        // Arrange
        String email = "test@example.com";
        String password = "password123";
        LoginDTO loginDTO = new LoginDTO(email, password);
        User mockUser = Mockito.mock(User.class);
        String expectedToken = "mockedAccessToken";

        Mockito.when(jwtUserDetailService.loadUserByUsername(email)).thenReturn(mockUser);
        Mockito.when(jwtProprieties.getAccessTokenExpiration()).thenReturn(3600000L);
        Mockito.when(jwtService.generate(Mockito.any(User.class), Mockito.any(Date.class), Mockito.anyMap())).thenReturn(expectedToken);

        // Act
        AuthDTO authDTO = authService.login(loginDTO);

        // Assert
        Assertions.assertNotNull(authDTO);
        Assertions.assertEquals(expectedToken, authDTO.accessToken());
        Assertions.assertEquals("bearer", authDTO.type());

        Mockito.verify(authManager).authenticate(Mockito.any(UsernamePasswordAuthenticationToken.class));
        Mockito.verify(jwtService).generate(Mockito.eq(mockUser), Mockito.any(Date.class), Mockito.anyMap());
    }

    @Test
    void shouldThrowExceptionWhenUserNotFound() {
        // Arrange
        String email = "nonexistent@example.com";
        String password = "password123";
        LoginDTO loginDTO = new LoginDTO(email, password);

        Mockito.when(jwtUserDetailService.loadUserByUsername(email))
                .thenThrow(new UsernameNotFoundException("User not found"));

        // Act & Assert
        Assertions.assertThrows(UsernameNotFoundException.class, () -> authService.login(loginDTO));
    }

    @Test
    void shouldThrowExceptionWhenAuthenticationFails() {
        // Arrange
        String email = "test@example.com";
        String password = "wrongPassword";
        LoginDTO loginDTO = new LoginDTO(email, password);
        User mockUser = mock(User.class);

        Mockito.when(jwtUserDetailService.loadUserByUsername(email)).thenReturn(mockUser);
        Mockito.when(jwtProprieties.getAccessTokenExpiration()).thenReturn(3600000L);
        Mockito.when(jwtService.generate(Mockito.any(User.class), Mockito.any(Date.class), Mockito.anyMap())).thenReturn("mockedAccessToken");
        Mockito.when(authManager.authenticate(Mockito.any(UsernamePasswordAuthenticationToken.class)))
                .thenThrow(new BadCredentialsException("Invalid credentials"));

        // Act & Assert
        Assertions.assertThrows(BadCredentialsException.class, () -> authService.login(loginDTO));

        Mockito.verify(authManager).authenticate(Mockito.any(UsernamePasswordAuthenticationToken.class));
    }
}

