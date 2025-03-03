package io.api.carrent.app.ports.repositories.queries;

import io.api.carrent.domain.dto.input.VehicleQueryDTO;
import io.api.carrent.domain.dto.input.VehicleStatusQueryDTO;
import io.api.carrent.domain.dto.output.VehicleDTO;
import io.api.carrent.domain.dto.output.VehicleStatusDTO;

import java.util.List;
import java.util.Optional;

public interface IVehicleQueryRepository {
    Optional<VehicleDTO> findById(Long vehicleId);
    List<VehicleDTO> findAll(VehicleQueryDTO filter);
    List<VehicleStatusDTO> findAllStatusHistory(VehicleStatusQueryDTO filter);
}
