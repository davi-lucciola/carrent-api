package io.api.carrent.infra.repositories.commands.jpa;

import io.api.carrent.domain.entities.VehicleRent;
import io.api.carrent.domain.enums.RentStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface VehicleRentJpaRepository extends JpaRepository<VehicleRent, Long> {
    @Query("SELECT vr FROM VehicleRent vr INNER JOIN vr.vehicle WHERE vr.withdrawMaxDatetime < ?1 AND vr.status = ?2")
    List<VehicleRent> findAllExpired(LocalDateTime now, RentStatus status);
}
