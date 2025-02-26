package io.api.carrent.services;

import io.api.carrent.dto.input.CreateUserDTO;
import io.api.carrent.dto.output.UserDTO;
import io.api.carrent.entities.Role;
import io.api.carrent.entities.User;
import io.api.carrent.entities.enums.Roles;
import io.api.carrent.exceptions.DomainException;
import io.api.carrent.repositories.UserJpaRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.Instant;
import java.util.Optional;
import java.util.Set;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private UserJpaRepository userRepository;

    @InjectMocks
    private UserService userService;

    private CreateUserDTO createUserDTO() {
        return new CreateUserDTO("John Doe", "john.doe@example.com", "password123");
    }

    private User createUser(CreateUserDTO createUserDTO) {
        Role role = new Role();
        role.setId(Roles.ADMIN.getId());
        Set<Role> roles = Set.of(role);
        Instant createdAt = Instant.now();

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
        Assertions.assertEquals(expectedUserDTO.id(), result.id());
        Assertions.assertEquals(expectedUserDTO.name(), result.name());
        Assertions.assertEquals(expectedUserDTO.email(), result.email());
        Assertions.assertEquals(expectedUserDTO.flActive(), result.flActive());
        Assertions.assertEquals(expectedUserDTO.roles(), result.roles());
        Assertions.assertEquals(expectedUserDTO.createdAt(), result.createdAt());

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


