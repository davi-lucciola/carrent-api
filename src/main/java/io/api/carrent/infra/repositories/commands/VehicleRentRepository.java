package io.api.carrent.infra.repositories.commands;

import io.api.carrent.app.ports.repositories.command.IVehicleRentRepository;
import io.api.carrent.domain.entities.VehicleRent;
import io.api.carrent.domain.entities.VehicleStatusHistory;
import io.api.carrent.domain.enums.RentStatus;
import io.api.carrent.infra.repositories.commands.jpa.VehicleJpaRepository;
import io.api.carrent.infra.repositories.commands.jpa.VehicleRentJpaRepository;
import io.api.carrent.infra.repositories.commands.jpa.VehicleStatusHistoryJpaRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class VehicleRentRepository implements IVehicleRentRepository {
    private final VehicleJpaRepository vehicleJpaRepository;
    private final VehicleRentJpaRepository vehicleRentJpaRepository;
    private final VehicleStatusHistoryJpaRepository vehicleStatusHistoryJpaRepository;

    @Override
    @Transactional
    public VehicleRent save(VehicleRent vehicleRent) {
        vehicleJpaRepository.save(vehicleRent.getVehicle());

        var vehicleStatusHistory = new VehicleStatusHistory(vehicleRent.getVehicle(), vehicleRent);
        vehicleStatusHistoryJpaRepository.save(vehicleStatusHistory);

        return vehicleRentJpaRepository.save(vehicleRent);
    }

    @Override
    public Optional<VehicleRent> findById(Long vehicleId) {
        return vehicleRentJpaRepository.findById(vehicleId);
    }

    @Override
    public List<VehicleRent> findAllExpired() {
        return vehicleRentJpaRepository.findAllExpired(LocalDateTime.now(), RentStatus.WATING);
    }
}
