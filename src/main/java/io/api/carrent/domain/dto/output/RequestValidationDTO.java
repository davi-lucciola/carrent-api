package io.api.carrent.domain.dto.output;

public record RequestValidationDTO(
        String field,
        String message
) {}
