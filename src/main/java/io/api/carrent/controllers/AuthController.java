package io.api.carrent.controllers;

import io.api.carrent.dto.output.AuthDTO;
import io.api.carrent.dto.output.UserDTO;
import io.api.carrent.entities.User;
import io.api.carrent.services.AuthService;
import jakarta.validation.Valid;
import io.api.carrent.dto.input.LoginDTO;
import io.api.carrent.dto.output.MessageDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthController {
    private final AuthService authService;

    @GetMapping("/me")
    public UserDTO getMe() {
        var user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        return new UserDTO(user.getId(), user.getName(),
                user.getEmail(), user.getFlActive(), user.getCreatedAt());
    }

    @PostMapping("/login")
    public AuthDTO login(@RequestBody @Valid LoginDTO loginDTO) {
        return authService.login(loginDTO);
    }
}
