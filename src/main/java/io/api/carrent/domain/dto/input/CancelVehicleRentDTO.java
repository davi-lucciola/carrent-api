package io.api.carrent.domain.dto.input;

import io.api.carrent.domain.entities.User;

public record CancelVehicleRentDTO(
        Long rentId,
        User currentUser,
        String cancellationReason
) {}
