package io.api.carrent.domain.exceptions;

import lombok.Getter;

@Getter
public class AuthException extends RuntimeException {
    public AuthException(String message) {
        super(message);
    }
}
