package io.api.carrent.api.controllers.command;

import io.api.carrent.domain.dto.input.LoginDTO;
import io.api.carrent.domain.dto.output.AuthDTO;
import io.api.carrent.domain.dto.output.UserDTO;
import io.api.carrent.domain.entities.User;
import io.api.carrent.domain.services.command.IAuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import static io.api.carrent.api.docs.SwaggerConstants.AUTH_TAG;
import static io.api.carrent.api.docs.SwaggerConstants.SECURITY_BEARER;

@Tag(name = AUTH_TAG)
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthController {
    private final IAuthService authService;

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
