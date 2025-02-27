package io.api.carrent.domain.ports.services;

import io.api.carrent.domain.dto.input.CreateUserDTO;
import io.api.carrent.domain.dto.output.UserDTO;

public interface IUserService {
    UserDTO create(CreateUserDTO createUserDTO);
}
