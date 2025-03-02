package io.api.carrent.domain.dto.input;

import io.api.carrent.domain.entities.User;

public record BookVehicleRentDTO(
        Long vehicleId,
        User renterUser
) {}
