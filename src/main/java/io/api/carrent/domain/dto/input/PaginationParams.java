package io.api.carrent.domain.dto.input;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PaginationParams {
    protected Integer page = 1;
    protected Integer perPage = 10;
}
