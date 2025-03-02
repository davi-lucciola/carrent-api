package io.api.carrent.api.controllers.queries;

import io.api.carrent.domain.dto.input.VehicleQueryDTO;
import io.api.carrent.domain.enums.VehicleStatus;
import io.api.carrent.domain.services.queries.IVehicleQueryService;
import io.api.carrent.domain.dto.output.VehicleDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static io.api.carrent.api.docs.SwaggerConstants.SECURITY_BEARER;
import static io.api.carrent.api.docs.SwaggerConstants.VEHICLE_TAG;

@Tag(name = VEHICLE_TAG)
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/vehicles")
public class VehicleQueryController {
    private final IVehicleQueryService vehicleQueryService;

    @GetMapping
    @SecurityRequirement(name = SECURITY_BEARER)
    @Operation(description = "Busca o todos os veículos.")
    public ResponseEntity<List<VehicleDTO>> findAllVehicles(
            @RequestParam(required = false) String search,
            @RequestParam(required = false) VehicleStatus status,
            @RequestParam(required = false) Long vehicleTypeId,
            @RequestParam(required = false) Boolean flActive
    ) {
        var filter = new VehicleQueryDTO(search, status, vehicleTypeId, flActive);
        return new ResponseEntity<>(vehicleQueryService.findAll(filter), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @SecurityRequirement(name = SECURITY_BEARER)
    @Operation(description = "Busca um veículo pelo id.")
    public ResponseEntity<VehicleDTO> detailVehicle(@PathVariable Long id) {
        return new ResponseEntity<>(vehicleQueryService.findById(id), HttpStatus.OK);
    }
}
