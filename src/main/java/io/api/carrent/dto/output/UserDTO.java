package io.api.carrent.dto.output;

import java.time.LocalDateTime;
import java.util.List;

public record UserDTO(
        Long id,
        String name,
        String email,
        Boolean flActive,
        List<String> roles,
        LocalDateTime createdAt
) {}
