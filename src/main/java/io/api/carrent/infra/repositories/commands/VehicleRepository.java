package io.api.carrent.infra.repositories.commands;

import io.api.carrent.core.ports.repositories.command.IVehicleRepository;
import io.api.carrent.domain.entities.Vehicle;
import io.api.carrent.infra.repositories.commands.jpa.VehicleJpaRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class VehicleRepository implements IVehicleRepository {
    private final VehicleJpaRepository vehicleJpaRepository;

    @Override
    @Transactional
    public Vehicle save(Vehicle vehicle) {
        return vehicleJpaRepository.save(vehicle);
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
}
