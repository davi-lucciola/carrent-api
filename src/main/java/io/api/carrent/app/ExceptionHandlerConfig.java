package io.api.carrent.app;

import io.api.carrent.domain.dto.output.MessageDTO;
import io.api.carrent.domain.dto.output.RequestValidationDTO;
import io.api.carrent.domain.exceptions.DomainException;
import io.api.carrent.domain.exceptions.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.ArrayList;
import java.util.List;

@ControllerAdvice
public class ExceptionHandlerConfig {
    @ExceptionHandler(DomainException.class)
    public ResponseEntity<MessageDTO> domainExceptionHandler(DomainException exception) {
        var messageResponse = new MessageDTO(exception.getMessage());
        return new ResponseEntity<>(messageResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<MessageDTO> notFoundExceptionHandler(NotFoundException exception) {
        var messageResponse = new MessageDTO(exception.getMessage());
        return new ResponseEntity<>(messageResponse, HttpStatus.NOT_FOUND);
    }

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
}
