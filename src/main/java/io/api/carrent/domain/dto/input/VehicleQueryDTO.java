package io.api.carrent.domain.dto.input;

import io.api.carrent.domain.enums.VehicleStatus;
import jakarta.validation.constraints.NotEmpty;
import org.springframework.web.bind.annotation.RequestParam;

public record VehicleQueryDTO(
        @RequestParam
        @NotEmpty(message = "O campo 'search' não pode ser vazio.")
        String search,

        @RequestParam
        VehicleStatus status,

        @RequestParam
        Long vehicleTypeId,

        @RequestParam
        Boolean flActive
) {}
