package io.api.carrent.controllers;

import io.api.carrent.entities.Vehicle;
import io.api.carrent.services.VehicleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static io.api.carrent.config.SwaggerConstants.VEHICLE_TAG;
import static io.api.carrent.config.SwaggerConstants.SECURITY_BEARER;

@Tag(name = VEHICLE_TAG)
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/vehicles")
public class VehicleController {
    private final VehicleService vehicleService;

    @GetMapping
    @SecurityRequirement(name = SECURITY_BEARER)
    @Operation(description = "Busca o todos os veículos.")
    public ResponseEntity<List<Vehicle>> findAllVehicles() {
        return new ResponseEntity<>(vehicleService.findAll(), HttpStatus.OK);
    }
}
