package io.api.carrent.domain.dto.input;

import io.api.carrent.domain.entities.User;

public record ReturnVehicleRentDTO(
        String returnCode,
        Long rentId,
        User returnOperatorUser
) { }
