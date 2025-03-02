package io.api.carrent.infra.repositories.commands.jpa;

import io.api.carrent.domain.entities.VehicleStatusHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VehicleStatusHistoryJpaRepository extends JpaRepository<VehicleStatusHistory, Long> {
}
