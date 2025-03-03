package io.api.carrent.domain.dto.input;

import io.api.carrent.domain.enums.RentStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class VehicleRentQueryBasicDTO extends PaginationParams {
    protected String search;
    protected RentStatus status;
}
