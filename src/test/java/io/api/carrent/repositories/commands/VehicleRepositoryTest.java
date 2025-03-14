package io.api.carrent.repositories.commands;

import io.api.carrent.domain.entities.Vehicle;
import io.api.carrent.domain.entities.VehicleType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureDataJpa;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
@AutoConfigureDataJpa
class VehicleRepositoryTest {

    @Autowired
    private VehicleRepository vehicleRepository;

    @Autowired
    private VehicleTypeRepository vehicleTypeRepository;

    @Test
    void testSaveVehicle() {
        var vehicleType = vehicleTypeRepository.save(new VehicleType("Carro"));
        String plate = "AAC0023";
        Vehicle vehicle = new Vehicle(
                plate,
                "Ford",
                "Ka",
                2020,
                40000F,
                vehicleType
        );

        var result = vehicleRepository.findByPlate(plate);
        Assertions.assertTrue(result.isPresent());
    }
}