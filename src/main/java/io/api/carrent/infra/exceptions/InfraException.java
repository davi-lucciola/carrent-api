package io.api.carrent.infra.exceptions;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class InfraException extends RuntimeException {
    public InfraException(String message) {
        super(message);
    }
}
