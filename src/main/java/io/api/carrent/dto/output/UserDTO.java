package io.api.carrent.dto.output;

import java.time.LocalDateTime;

public record UserDTO(
        Long id,
        String name,
        String email,
        Boolean flActive,
        LocalDateTime createdAt
) {
}
