package io.api.carrent.domain.ports.services;

import io.api.carrent.domain.dto.input.LoginDTO;
import io.api.carrent.domain.dto.output.AuthDTO;

public interface IAuthService {
    AuthDTO login(LoginDTO loginDTO);
}
