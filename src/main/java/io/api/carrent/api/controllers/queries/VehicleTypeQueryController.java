package io.api.carrent.api.controllers.queries;

import io.api.carrent.domain.dto.output.VehicleTypeDTO;
import io.api.carrent.domain.services.queries.IVehicleTypeQueryService;
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

import static io.api.carrent.api.docs.SwaggerConstants.SECURITY_BEARER;
import static io.api.carrent.api.docs.SwaggerConstants.VEHICLE_TYPE_TAG;

@Tag(name = VEHICLE_TYPE_TAG)
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/vehicles/types")
public class VehicleTypeQueryController {
    private final IVehicleTypeQueryService vehicleTypeQueryService;

    @GetMapping
    @SecurityRequirement(name = SECURITY_BEARER)
    @Operation(description = "Busca o todos os tipos de veículos .")
    public ResponseEntity<List<VehicleTypeDTO>> findAllVehicleTypes() {
        return new ResponseEntity<>(vehicleTypeQueryService.findAll(), HttpStatus.OK);
    }
}
