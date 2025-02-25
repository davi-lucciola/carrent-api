package io.api.carrent.repositories;

import io.api.carrent.dto.output.VehicleDTO;
import io.api.carrent.entities.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface VehicleJpaRepository extends JpaRepository<Vehicle, Long> {
    @Query("""
        SELECT new io.api.carrent.dto.output.VehicleDTO(
            v.id,
            v.plate,
            v.brand,
            v.model,
            v.year,
            v.odometer,
            v.vehicleType.name,
            v.status,
            v.flActive,
            v.createdAt
        )
        FROM Vehicle v
        INNER JOIN v.vehicleType
    """)
    List<VehicleDTO> findAllDto();

    @Query("SELECT v FROM Vehicle v WHERE v.plate = ?1")
    Optional<Vehicle> findByPlate(String plate);

    @Query("SELECT v FROM Vehicle v WHERE v.vehicleType.id = ?1")
    List<Vehicle> findAllByVehicleType(Integer vehicleTypeId);
}
