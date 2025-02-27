package io.api.carrent.domain.ports.repositories;

import io.api.carrent.domain.entities.User;

import java.util.Optional;

public interface IUserRepository {
    Optional<User> findByEmail(String email);
    User save(User user);
}
