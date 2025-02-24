package io.api.carrent.controllers;

import io.api.carrent.dto.output.UserDTO;
import io.api.carrent.dto.input.CreateUserDTO;
import io.api.carrent.services.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserDTO create(@RequestBody @Valid CreateUserDTO request) {
        return userService.create(request);
    }
}
