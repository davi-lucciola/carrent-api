package io.api.carrent.domain.dto.output;

import java.time.Instant;
import java.util.List;

public record UserDTO(
        Long id,
        String name,
        String email,
        Boolean flActive,
        List<String> roles,
        Instant createdAt
) {}
