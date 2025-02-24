package io.api.carrent.dto.input;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record LoginDTO(
        @Email(message = "Email inválido.")
        @NotBlank(message = "O campo 'email' é obrigatório.")
        String email,

        @NotBlank(message = "O campo 'password' é obrigatório.")
        String password
) {}
