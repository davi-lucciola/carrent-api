package io.api.carrent.services;

import io.api.carrent.dto.input.CreateUserDTO;
import io.api.carrent.dto.output.UserDTO;
import io.api.carrent.entities.User;
import io.api.carrent.exceptions.DomainException;
import io.api.carrent.repositories.UserJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final PasswordEncoder passwordEncoder;
    private final UserJpaRepository userRepository;

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
