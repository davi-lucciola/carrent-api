package io.api.carrent.controllers;

import io.api.carrent.dto.input.CreateUserDTO;
import io.api.carrent.dto.output.UserDTO;
import io.api.carrent.services.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static io.api.carrent.config.SwaggerConstants.USER_TAG;

@Tag(name = USER_TAG)
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;

    @PostMapping
    @Operation(description = "Cria um novo usuário")
    public ResponseEntity<UserDTO> create(@RequestBody @Valid CreateUserDTO userDTO) {
        return new ResponseEntity<>(userService.create(userDTO), HttpStatus.CREATED);
    }
}
