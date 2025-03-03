package io.api.carrent.app.ports.repositories.queries;

import io.api.carrent.domain.dto.input.VehicleRentQueryDTO;
import io.api.carrent.domain.dto.output.VehicleRentDTO;
import io.api.carrent.domain.dto.output.VehicleRentDetailDTO;
import io.api.carrent.domain.entities.User;

import java.util.List;
import java.util.Optional;

public interface IVehicleRentQueryRepository {
    List<VehicleRentDTO> findAll(VehicleRentQueryDTO filter);
    Optional<VehicleRentDetailDTO> findById(Long rentId, User user);
}
