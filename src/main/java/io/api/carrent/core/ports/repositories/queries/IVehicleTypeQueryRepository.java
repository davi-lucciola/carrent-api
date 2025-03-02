package io.api.carrent.core.ports.repositories.queries;

import io.api.carrent.domain.dto.output.VehicleTypeDTO;

import java.util.List;

public interface IVehicleTypeQueryRepository {
    List<VehicleTypeDTO> findAll();
}
