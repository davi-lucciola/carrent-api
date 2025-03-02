package io.api.carrent.domain.enums;

import lombok.Getter;

@Getter
public enum VehicleStatus {
    AVAILABLE("Disponivel"),
    BOOKED("Reservado"),
    RENTED("Alugado");

    private final String description;

    VehicleStatus(String description) {
        this.description = description;
    }
}
