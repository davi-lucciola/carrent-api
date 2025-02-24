package io.api.carrent.security.jwt;

import io.api.carrent.entities.User;
import io.api.carrent.repositories.UserJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class JwtUserDetailService implements UserDetailsService {
    private final UserJpaRepository userRepository;

    @Override
    public User loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findByEmail(email);

        if (user.isEmpty()) {
            throw new UsernameNotFoundException("Usuário não encontrado.");
        }

        return user.get();
    }
}
