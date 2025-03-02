package io.api.carrent.api;

import io.api.carrent.domain.dto.output.MessageDTO;
import io.api.carrent.domain.dto.output.RequestValidationDTO;
import io.api.carrent.domain.exceptions.DomainException;
import io.api.carrent.domain.exceptions.NoContentException;
import io.api.carrent.domain.exceptions.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authorization.AuthorizationDeniedException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.ArrayList;
import java.util.List;

@ControllerAdvice
public class ExceptionHandlerConfig {
    // Generic Exceptions
    @ExceptionHandler(Exception.class)
    public ResponseEntity<MessageDTO> genericExceptionHandler(Exception exception) {
        exception.printStackTrace();
        var messageResponse = new MessageDTO("Não foi possivel processar sua solicitação.");
        return new ResponseEntity<>(messageResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    // Domain Exceptions
    @ExceptionHandler(DomainException.class)
    public ResponseEntity<MessageDTO> domainExceptionHandler(DomainException exception) {
        var messageResponse = new MessageDTO(exception.getMessage());
        return new ResponseEntity<>(messageResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NoContentException.class)
    public ResponseEntity<String> noContentExceptionHandler(NoContentException exception) {
        return new ResponseEntity<>("", HttpStatus.NO_CONTENT);
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<MessageDTO> notFoundExceptionHandler(NotFoundException exception) {
        var messageResponse = new MessageDTO(exception.getMessage());
        return new ResponseEntity<>(messageResponse, HttpStatus.NOT_FOUND);
    }

    // Input Data Exceptions
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<List<RequestValidationDTO>> methodArgumentNotValidExceptionHandler(
        MethodArgumentNotValidException exception
    ) {
        var errors = exception.getBindingResult().getAllErrors();
        var validationResponses = new ArrayList<RequestValidationDTO>();

        for (var error : errors) {
            String field = ((FieldError) error).getField();
            String message = error.getDefaultMessage();
            validationResponses.add(new RequestValidationDTO(field, message));
        }

        return new ResponseEntity<>(validationResponses, HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @ExceptionHandler(AuthorizationDeniedException.class)
    public ResponseEntity<MessageDTO> AuthorizationDeniedExceptionHandler(AuthorizationDeniedException exception) {
        var messageResponse = new MessageDTO("Usuário não autorizado.");
        return new ResponseEntity<>(messageResponse, HttpStatus.FORBIDDEN);
    }
}
