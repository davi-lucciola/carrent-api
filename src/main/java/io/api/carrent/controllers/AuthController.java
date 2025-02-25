package io.api.carrent.controllers;

import io.api.carrent.dto.input.LoginDTO;
import io.api.carrent.dto.output.AuthDTO;
import io.api.carrent.dto.output.UserDTO;
import io.api.carrent.entities.User;
import io.api.carrent.services.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import static io.api.carrent.config.SwaggerConstants.AUTH_TAG;
import static io.api.carrent.config.SwaggerConstants.SECURITY_BEARER;

@Tag(name = AUTH_TAG)
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthController {
    private final AuthService authService;

    @GetMapping("/me")
    @SecurityRequirement(name = SECURITY_BEARER)
    @Operation(description = "Busca o usuário atual autenticado.")
    public ResponseEntity<UserDTO> getMe() {
        var user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return new ResponseEntity<>(user.toDTO(), HttpStatus.OK);
    }

    @PostMapping("/login")
    @Operation(description = "Autentica o usuário através de um login e retorna um token JWT.")
    public ResponseEntity<AuthDTO> login(@RequestBody @Valid LoginDTO loginDTO) {
        return new ResponseEntity<>(authService.login(loginDTO), HttpStatus.OK);
    }
}
