package io.api.carrent.domain.services.queries;

import io.api.carrent.domain.dto.input.VehicleRentQueryDTO;
import io.api.carrent.domain.dto.output.Pagination;
import io.api.carrent.domain.dto.output.VehicleRentDTO;

public interface IVehicleRentQueryService {
    Pagination<VehicleRentDTO> findAll(VehicleRentQueryDTO filter);
}
