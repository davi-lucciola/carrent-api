package io.api.carrent.infra.exceptions;

import lombok.Getter;

@Getter
public class MappingException extends InfraException {
    public MappingException(String message) {
        super(message);
    }
}
