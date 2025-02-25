package io.api.carrent.dto.input;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record SaveVehicleDTO(
        @NotBlank(message = "O campo 'plate' é obrigatório.")
        String plate,

        @NotBlank(message = "O campo 'brand' é obrigatório.")
        String brand,

        @NotBlank(message = "O campo 'model' é obrigatório.")
        String model,

        @NotNull(message = "O campo 'year' é obrigatório")
        Integer year,

        @NotNull(message = "O campo 'odometer' é obrigatório")
        @Min(message = "O campo 'odometer' não pode ser negativo", value = 0)
        Float odometer,

        @NotNull(message = "O campo 'vehicleTypeId' é obrigatório.")
        Integer vehicleTypeId
) {}
