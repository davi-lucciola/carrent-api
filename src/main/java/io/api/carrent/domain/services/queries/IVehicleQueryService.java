package io.api.carrent.domain.services.queries;

import io.api.carrent.domain.dto.input.VehicleQueryDTO;
import io.api.carrent.domain.dto.output.VehicleDTO;

import java.util.List;

public interface IVehicleQueryService {
    List<VehicleDTO> findAll(VehicleQueryDTO filter);
    VehicleDTO findById(Long vehicleId);
}
