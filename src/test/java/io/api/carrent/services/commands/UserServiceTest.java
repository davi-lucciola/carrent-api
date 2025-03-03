package io.api.carrent.services.commands;

import io.api.carrent.app.ports.repositories.command.IUserRepository;
import io.api.carrent.app.services.command.UserService;
import io.api.carrent.domain.dto.input.CreateUserDTO;
import io.api.carrent.domain.dto.output.UserDTO;
import io.api.carrent.domain.entities.Role;
import io.api.carrent.domain.entities.User;
import io.api.carrent.domain.enums.Roles;
import io.api.carrent.domain.exceptions.DomainException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Set;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private IUserRepository userRepository;

    @InjectMocks
    private UserService userService;

    private CreateUserDTO createUserDTO() {
        return new CreateUserDTO("John Doe", "john.doe@example.com", "password123");
    }

    private User createUser(CreateUserDTO createUserDTO) {
        Role role = new Role();
        role.setId(Roles.ADMIN.getId());
        Set<Role> roles = Set.of(role);
        LocalDateTime createdAt = LocalDateTime.now();

        User user = new User(createUserDTO.name(), createUserDTO.email(), "encodedPassword");
        user.setId(1L);
        user.setFlActive(true);
        user.setRoles(roles);
        user.setCreatedAt(createdAt);

        return user;
    }

    @Test
    void shouldCreateUserSuccessfully() {
        // Arrange
        CreateUserDTO createUserDTO = createUserDTO();
        User user = createUser(createUserDTO);
        UserDTO expectedUserDTO = user.toDTO();

        Mockito.when(userRepository.findByEmail(createUserDTO.email())).thenReturn(Optional.empty());
        Mockito.when(passwordEncoder.encode(createUserDTO.password())).thenReturn("encodedPassword");
        Mockito.when(userRepository.save(Mockito.any(User.class))).thenReturn(user);

        // Act
        UserDTO result = userService.create(createUserDTO);

        // Assert
        Assertions.assertNotNull(result);
        Assertions.assertEquals(expectedUserDTO.getId(), result.getId());
        Assertions.assertEquals(expectedUserDTO.getName(), result.getName());
        Assertions.assertEquals(expectedUserDTO.getEmail(), result.getEmail());
        Assertions.assertEquals(expectedUserDTO.getFlActive(), result.getFlActive());
        Assertions.assertEquals(expectedUserDTO.getRoles(), result.getRoles());
        Assertions.assertEquals(expectedUserDTO.getCreatedAt(), result.getCreatedAt());

        Mockito.verify(userRepository).findByEmail(createUserDTO.email());
        Mockito.verify(passwordEncoder).encode(createUserDTO.password());
        Mockito.verify(userRepository).save(Mockito.any(User.class));
    }

    @Test
    void shouldThrowExceptionWhenUserAlreadyExists() {
        // Arrange
        CreateUserDTO createUserDTO = createUserDTO();
        User existingUser = createUser(createUserDTO);

        Mockito.when(userRepository.findByEmail(createUserDTO.email())).thenReturn(Optional.of(existingUser));

        // Act & Assert
        Assertions.assertThrows(DomainException.class, () -> userService.create(createUserDTO));

        Mockito.verify(userRepository).findByEmail(createUserDTO.email());
        Mockito.verifyNoInteractions(passwordEncoder);
        Mockito.verifyNoMoreInteractions(userRepository);
    }
}


