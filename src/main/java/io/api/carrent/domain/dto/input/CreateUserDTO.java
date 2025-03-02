package io.api.carrent.domain.dto.input;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record CreateUserDTO(
        @NotBlank(message = "O campo 'name' é obrigatório.")
        String name,

        @Email(message = "Email inválido.")
        @NotBlank(message = "O campo 'email' é obrigatório.")
        String email,

        @NotBlank(message = "O campo 'password é obrigatório.'")
        String password
) {}
