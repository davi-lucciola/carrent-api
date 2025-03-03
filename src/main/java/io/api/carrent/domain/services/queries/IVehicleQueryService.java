package io.api.carrent.domain.services.queries;

import io.api.carrent.domain.dto.input.VehicleQueryDTO;
import io.api.carrent.domain.dto.input.VehicleStatusQueryDTO;
import io.api.carrent.domain.dto.output.Pagination;
import io.api.carrent.domain.dto.output.VehicleDTO;
import io.api.carrent.domain.dto.output.VehicleStatusDTO;

public interface IVehicleQueryService {
    VehicleDTO findById(Long vehicleId);
    Pagination<VehicleDTO> findAll(VehicleQueryDTO filter);
    Pagination<VehicleStatusDTO> findAllStatus(VehicleStatusQueryDTO filter);
}
