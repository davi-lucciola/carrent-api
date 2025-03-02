package io.api.carrent.domain.services.command;

import io.api.carrent.domain.dto.input.CreateUserDTO;
import io.api.carrent.domain.dto.output.UserDTO;

public interface IUserService {
    UserDTO create(CreateUserDTO createUserDTO);
}
