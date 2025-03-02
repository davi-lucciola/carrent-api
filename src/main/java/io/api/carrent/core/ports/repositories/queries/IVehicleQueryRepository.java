package io.api.carrent.core.ports.repositories.queries;

import io.api.carrent.domain.dto.input.VehicleQueryDTO;
import io.api.carrent.domain.dto.output.VehicleDTO;

import java.util.List;

public interface IVehicleQueryRepository {
    List<VehicleDTO> findAll(VehicleQueryDTO filter);
    VehicleDTO findById(Long vehicleId);
}
