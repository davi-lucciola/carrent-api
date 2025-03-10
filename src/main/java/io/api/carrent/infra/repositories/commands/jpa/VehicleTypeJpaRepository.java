package io.api.carrent.infra.repositories.commands.jpa;

import io.api.carrent.domain.entities.VehicleType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface VehicleTypeJpaRepository extends JpaRepository<VehicleType, Integer> {
    @Query("SELECT vt FROM VehicleType vt WHERE vt.name = ?1")
    Optional<VehicleType> findByName(String name);
}
