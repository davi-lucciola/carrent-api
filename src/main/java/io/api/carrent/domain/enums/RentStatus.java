package io.api.carrent.domain.enums;

import lombok.Getter;

@Getter
public enum RentStatus {
    WATING("Aguardando Confirmação"),
    CANCELED("Cancelada"),
    IN_PROGRESS("Em Andamento"),
    FINISHED("Finalizada");

    private final String description;

    RentStatus(String description) {
        this.description = description;
    }
}
