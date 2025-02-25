package io.api.carrent.dto.input;

import jakarta.validation.constraints.NotBlank;

public record SaveVehicleTypeDTO(
        @NotBlank(message = "O campo 'name' é obrigatório.")
        String name
) {}
