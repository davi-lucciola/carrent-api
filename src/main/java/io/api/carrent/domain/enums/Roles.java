package io.api.carrent.domain.enums;

import lombok.Getter;

@Getter
public enum Roles {
    ADMIN(1),
    CUSTOMER(2);

    private final Integer id;

    Roles(Integer id) {
        this.id = id;
    }
}