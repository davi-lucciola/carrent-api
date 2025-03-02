package io.api.carrent.domain.dto.input;

import jakarta.validation.constraints.NotBlank;

public record CancellationReasonDTO(
        @NotBlank(message = "O campo 'cancellationReason' é obrigatório.")
        String cancellationReason
) {}
