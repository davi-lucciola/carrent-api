package io.api.carrent.infra.repositories.commands;

import io.api.carrent.domain.entities.VehicleType;
import io.api.carrent.core.ports.repositories.command.IVehicleTypeRepository;
import io.api.carrent.infra.repositories.commands.jpa.VehicleTypeJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class VehicleTypeRepository implements IVehicleTypeRepository {
    private final VehicleTypeJpaRepository vehicleTypeJpaRepository;

    @Override
    public Optional<VehicleType> findById(Integer id) {
        return vehicleTypeJpaRepository.findById(id);
    }

    @Override
    public Optional<VehicleType> findByName(String name) {
        return vehicleTypeJpaRepository.findByName(name);
    }

    @Override
    public VehicleType save(VehicleType vehicleType) {
        return vehicleTypeJpaRepository.save(vehicleType);
    }

    @Override
    public void delete(VehicleType vehicleType) {
        vehicleTypeJpaRepository.delete(vehicleType);
    }
}
