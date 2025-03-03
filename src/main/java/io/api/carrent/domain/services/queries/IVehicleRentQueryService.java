package io.api.carrent.domain.services.queries;

import io.api.carrent.domain.dto.input.VehicleRentQueryDTO;
import io.api.carrent.domain.dto.output.Pagination;
import io.api.carrent.domain.dto.output.VehicleRentDTO;
import io.api.carrent.domain.dto.output.VehicleRentDetailDTO;
import io.api.carrent.domain.entities.User;

public interface IVehicleRentQueryService {
    Pagination<VehicleRentDTO> findAll(VehicleRentQueryDTO filter);
    VehicleRentDetailDTO findById(Long vehicleId, User user);
}
