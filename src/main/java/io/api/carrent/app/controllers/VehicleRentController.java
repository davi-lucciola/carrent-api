package io.api.carrent.app.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static io.api.carrent.infra.security.SecurityConstants.ROLE_ADMIN;
import static io.api.carrent.app.docs.SwaggerConstants.SECURITY_BEARER;
import static io.api.carrent.app.docs.SwaggerConstants.USER_TAG;

@Tag(name = USER_TAG)
@RestController
@RequestMapping("/api/vehicles/rent")
public class VehicleRentController {
    @PostMapping("/book")
    @SecurityRequirement(name = SECURITY_BEARER)
    @Operation(description = "Reserva um veículo para alugar. Alterando o status de 'Disponivel' para 'Reservado'.")
    public ResponseEntity bookVehicleRent() {
        return ResponseEntity.ok().build();
    }

    @PostMapping("/confirm")
    @PreAuthorize(ROLE_ADMIN)
    @SecurityRequirement(name = SECURITY_BEARER)
    @Operation(description = "Confirma a reserva do veículo. Alterando o status de 'Reservado' para 'Alugado' ")
    public ResponseEntity confirmVehicleRent() {
        return ResponseEntity.ok().build();
    }

    @PostMapping("/return")
    @PreAuthorize(ROLE_ADMIN)
    @SecurityRequirement(name = SECURITY_BEARER)
    @Operation(description = "Cria um novo usuário")
    public ResponseEntity returnVehicleRented() {
        return ResponseEntity.ok().build();
    }
}
