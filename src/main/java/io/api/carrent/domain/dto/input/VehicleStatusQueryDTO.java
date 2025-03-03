package io.api.carrent.domain.dto.input;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class VehicleStatusQueryDTO extends PaginationParams {
    private Long vehicleId;

    public VehicleStatusQueryDTO(Long vehicleId, Integer page, Integer perPage) {
        super(page, perPage);
        this.vehicleId = vehicleId;
    }
}
