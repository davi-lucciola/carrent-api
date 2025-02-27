package io.api.carrent.domain.dto.output;

import io.api.carrent.domain.enums.VehicleStatus;

import java.time.Instant;

public record VehicleDTO(
        Long id,
        String plate,
        String brand,
        String model,
        Integer year,
        Float odometer,
        String vehicleType,
        VehicleStatus status,
        Boolean flActive,
        Instant createdAt
) {}
