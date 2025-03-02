package io.api.carrent.domain.dto.input;

import jakarta.validation.constraints.NotEmpty;

public record RentCodeDTO(
        @NotEmpty(message = "O campo 'code' é obrigatório.")
        String code
) {}
