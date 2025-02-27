package io.api.carrent.infra.repositories;

import io.api.carrent.domain.dto.output.VehicleDTO;
import io.api.carrent.domain.entities.Vehicle;
import io.api.carrent.domain.ports.repositories.IVehicleRepository;
import io.api.carrent.infra.repositories.jpa.VehicleJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class VehicleRepository implements IVehicleRepository {
    private final VehicleJpaRepository vehicleJpaRepository;

    @Override
    public List<VehicleDTO> findAll() {
        return vehicleJpaRepository.findAllDto();
    }

    @Override
    public Optional<Vehicle> findById(Long id) {
        return vehicleJpaRepository.findById(id);
    }

    @Override
    public Optional<Vehicle> findByPlate(String plate) {
        return vehicleJpaRepository.findByPlate(plate);
    }

    @Override
    public List<Vehicle> findAllByVehicleType(Integer vehicleTypeId) {
        return vehicleJpaRepository.findAllByVehicleType(vehicleTypeId);
    }

    @Override
    public Vehicle save(Vehicle vehicle) {
        return vehicleJpaRepository.save(vehicle);
    }
}
