package io.api.carrent.repositories;

import io.api.carrent.entities.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VehicleJpaRepository extends JpaRepository<Vehicle, Long> {
    @Query("SELECT v FROM Vehicle v WHERE v.vehicleType.id = ?1")
    List<Vehicle> findAllByVehicleType(Integer vehicleTypeId);
}
