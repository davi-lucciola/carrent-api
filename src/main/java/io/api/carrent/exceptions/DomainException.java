package io.api.carrent.exceptions;

import lombok.Getter;

@Getter
public class DomainException extends RuntimeException {
    public DomainException(String message) {
        super(message);
    }
}
