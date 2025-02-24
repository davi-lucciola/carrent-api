package io.api.carrent.controllers;

import io.api.carrent.exceptions.DomainException;
import io.api.carrent.dto.output.MessageDTO;
import io.api.carrent.dto.output.RequestValidationDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;

import java.util.ArrayList;
import java.util.List;

@ControllerAdvice
public class ExceptionHandler {
    @org.springframework.web.bind.annotation.ExceptionHandler(DomainException.class)
    public ResponseEntity<MessageDTO> domainExceptionHandler(DomainException exception) {
        var messageResponse = new MessageDTO(exception.getMessage());
        return new ResponseEntity<>(messageResponse, HttpStatus.BAD_REQUEST);
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(MethodArgumentNotValidException.class)
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
