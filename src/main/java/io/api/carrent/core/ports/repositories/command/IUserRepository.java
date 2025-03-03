package io.api.carrent.core.ports.repositories.command;

import io.api.carrent.domain.entities.User;

import java.util.Optional;

public interface IUserRepository {
    User save(User user);
    Optional<User> findByEmail(String email);
}
