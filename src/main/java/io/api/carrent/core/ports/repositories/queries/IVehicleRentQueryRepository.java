package io.api.carrent.core.ports.repositories.queries;

import io.api.carrent.domain.dto.input.VehicleRentQueryDTO;
import io.api.carrent.domain.dto.output.VehicleRentDTO;

import java.util.List;

public interface IVehicleRentQueryRepository {
    List<VehicleRentDTO> findAll(VehicleRentQueryDTO filter);
}
