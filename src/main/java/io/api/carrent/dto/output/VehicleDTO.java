package io.api.carrent.dto.output;

import io.api.carrent.entities.VehicleType;

public record VehicleDTO(
        Long id,
        String plate,
        String brand,
        String model,
        Integer year,
        Float odometer,
        String vehicleType
) {
}
