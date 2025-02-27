package io.api.carrent.infra.cmd;

import io.api.carrent.domain.dto.input.CreateUserDTO;
import io.api.carrent.domain.entities.Role;
import io.api.carrent.domain.entities.User;
import io.api.carrent.domain.enums.Roles;
import io.api.carrent.infra.repositories.jpa.RoleJpaRepository;
import io.api.carrent.infra.repositories.jpa.UserJpaRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

@Configuration
@RequiredArgsConstructor
public class AdminUserCmd implements CommandLineRunner {
    private final PasswordEncoder passwordEncoder;
    private final UserJpaRepository userJpaRepository;
    private final RoleJpaRepository roleJpaRepository;
    private final Logger logger = LoggerFactory.getLogger(AdminUserCmd.class);

    @Override
    public void run(String... args) throws Exception {
        User userAdmin;
        CreateUserDTO createUserDTO = new CreateUserDTO(
                "Davi Lucciola", "davi@email.com", "1234");
        Optional<User> userOptional = userJpaRepository.findByEmail(createUserDTO.email());

        if (userOptional.isEmpty()) {
            userAdmin = new User(
                    createUserDTO.name(),
                    createUserDTO.email(),
                    passwordEncoder.encode(createUserDTO.password())
            );

            logger.info("Usuário admin cadastrado com sucesso.");
            userJpaRepository.saveAndFlush(userAdmin);
        } else {
            userAdmin = userOptional.get();
            logger.info("Usuário admin já cadastrado.");
        }

        if (userAdmin.getRoles().stream().noneMatch(role -> role.getId().equals(Roles.ADMIN.getId()))) {
            Role adminRole = roleJpaRepository.findById(Roles.ADMIN.getId())
                    .orElseThrow(() -> new RuntimeException("Role 'ADMIN' não encontrada."));

            userAdmin.getRoles().add(adminRole);
            userJpaRepository.saveAndFlush(userAdmin);
            logger.info("Role ADMIN adicionada ao usuário.");
        } else {
            logger.info("Role 'ADMIN' já foi adicionada ao usuário admin.");
        }
    }
}
