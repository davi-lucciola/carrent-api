package io.api.carrent.app.controllers;

import io.api.carrent.domain.dto.input.SaveVehicleDTO;
import io.api.carrent.domain.dto.output.MessageDTO;
import io.api.carrent.domain.dto.output.VehicleDTO;
import io.api.carrent.domain.entities.Vehicle;
import io.api.carrent.domain.ports.services.IVehicleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static io.api.carrent.infra.security.SecurityConstants.ROLE_ADMIN;
import static io.api.carrent.app.docs.SwaggerConstants.SECURITY_BEARER;
import static io.api.carrent.app.docs.SwaggerConstants.VEHICLE_TAG;

@Tag(name = VEHICLE_TAG)
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/vehicles")
public class VehicleController {
    private final IVehicleService vehicleService;

    @GetMapping
    @SecurityRequirement(name = SECURITY_BEARER)
    @Operation(description = "Busca o todos os veículos.")
    public ResponseEntity<List<VehicleDTO>> findAllVehicles() {
        return new ResponseEntity<>(vehicleService.findAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @SecurityRequirement(name = SECURITY_BEARER)
    @Operation(description = "Busca o todos os veículos.")
    public ResponseEntity<Vehicle> detailVehicle(@PathVariable Long id) {
        return new ResponseEntity<>(vehicleService.findById(id), HttpStatus.OK);
    }

    @PostMapping
    @PreAuthorize(ROLE_ADMIN)
    @SecurityRequirement(name = SECURITY_BEARER)
    @Operation(description = "Cadastra um novo veículo. (ADMIN)")
    public ResponseEntity<Vehicle> createVehicle(@RequestBody @Valid SaveVehicleDTO saveVehicleDTO) {
        return new ResponseEntity<>(vehicleService.create(saveVehicleDTO), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @PreAuthorize(ROLE_ADMIN)
    @SecurityRequirement(name = SECURITY_BEARER)
    @Operation(description = "Atualiza um veículo existente. (ADMIN)")
    public ResponseEntity<Vehicle> updateVehicle(
            @PathVariable Long id, @RequestBody @Valid SaveVehicleDTO saveVehicleDTO
    ) {
        return new ResponseEntity<>(vehicleService.update(id, saveVehicleDTO), HttpStatus.ACCEPTED);
    }

    @PatchMapping("/{id}/activate")
    @PreAuthorize(ROLE_ADMIN)
    @SecurityRequirement(name = SECURITY_BEARER)
    @Operation(description = "Ativa um veículo desativado. (ADMIN)")
    public ResponseEntity<MessageDTO> activateVehicle(@PathVariable Long id) {
        vehicleService.activate(id);
        return new ResponseEntity<>(new MessageDTO("Veículo ativado com sucesso."), HttpStatus.ACCEPTED);
    }

    @DeleteMapping("/{id}/deactivate")
    @PreAuthorize(ROLE_ADMIN)
    @SecurityRequirement(name = SECURITY_BEARER)
    @Operation(description = "Desativa um veículo ativo. (ADMIN)")
    public ResponseEntity<MessageDTO> deactivateVehicle(@PathVariable Long id) {
        vehicleService.deactivate(id);
        return new ResponseEntity<>(new MessageDTO("Veículo desativado com sucesso."), HttpStatus.ACCEPTED);
    }
}
