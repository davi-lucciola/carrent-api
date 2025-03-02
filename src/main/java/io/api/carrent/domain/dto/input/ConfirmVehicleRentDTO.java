package io.api.carrent.domain.dto.input;

import io.api.carrent.domain.entities.User;

public record ConfirmVehicleRentDTO(
        String withdrawCode,
        Long rentId,
        User withdrawOperatorUser
) {}
