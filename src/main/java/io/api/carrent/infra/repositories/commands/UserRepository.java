package io.api.carrent.infra.repositories.commands;

import io.api.carrent.app.ports.repositories.command.IUserRepository;
import io.api.carrent.domain.entities.User;
import io.api.carrent.infra.repositories.commands.jpa.UserJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class UserRepository implements IUserRepository {
    private final UserJpaRepository userJpaRepository;

    @Override
    public User save(User user) {
        return userJpaRepository.save(user);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return userJpaRepository.findByEmail(email);
    }
}
