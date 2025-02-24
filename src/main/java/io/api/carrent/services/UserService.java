package io.api.carrent.services;

import io.api.carrent.dto.output.UserDTO;
import io.api.carrent.entities.User;
import io.api.carrent.exceptions.DomainException;
import io.api.carrent.repositories.UserJpaRepository;
import io.api.carrent.dto.input.CreateUserDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final PasswordEncoder passwordEncoder;
    private final UserJpaRepository userRepository;

    public UserDTO create(CreateUserDTO request) {
        Optional<User> userOptional = userRepository.findByEmail(request.email());

        if (userOptional.isPresent()) {
            throw new DomainException("Já existe um usuário cadastrado com esse email.");
        }

        User user = new User(request.name(),
                request.email(), passwordEncoder.encode(request.password()));

        userRepository.saveAndFlush(user);

        return new UserDTO(user.getId(), user.getName(),
                user.getEmail(), user.getFlActive(), user.getCreatedAt());
    }
}
