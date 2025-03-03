package io.api.carrent.domain.dto.input;

import io.api.carrent.domain.enums.VehicleStatus;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
public class VehicleQueryDTO extends PaginationParams {
    private String search;
    private Boolean flActive;
    private Long vehicleTypeId;
    private VehicleStatus status;
}
