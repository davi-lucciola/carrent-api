package io.api.carrent.domain.services;

import io.api.carrent.domain.dto.input.CreateUserDTO;
import io.api.carrent.domain.dto.output.UserDTO;
import io.api.carrent.domain.entities.User;
import io.api.carrent.domain.exceptions.DomainException;
import io.api.carrent.domain.ports.repositories.IUserRepository;
import io.api.carrent.domain.ports.services.IUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService implements IUserService {
    private final PasswordEncoder passwordEncoder;
    private final IUserRepository userRepository;

    public UserDTO create(CreateUserDTO userDTO) {
        Optional<User> userOptional = userRepository.findByEmail(userDTO.email());

        if (userOptional.isPresent()) {
            throw new DomainException("Já existe um usuário cadastrado com esse email.");
        }

        User user = new User(userDTO.name(),
                userDTO.email(), passwordEncoder.encode(userDTO.password()));

        return userRepository.save(user).toDTO();
    }
}
