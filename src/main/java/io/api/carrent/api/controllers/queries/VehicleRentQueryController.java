package io.api.carrent.api.controllers.queries;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static io.api.carrent.api.docs.SwaggerConstants.RENT_VEHICLE_TAG;
import static io.api.carrent.api.docs.SwaggerConstants.SECURITY_BEARER;
import static io.api.carrent.infra.security.SecurityConstants.ROLE_ADMIN;

@Tag(name = RENT_VEHICLE_TAG)
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/vehicles/rents")
public class VehicleRentQueryController {

//    @GetMapping
//    @PreAuthorize(ROLE_ADMIN)
//    @SecurityRequirement(name = SECURITY_BEARER)
//    @Operation(description = "Busca todos os alugueis de veículos.")
//    public ResponseEntity findAllRents() {
//
//    }
//
//    @GetMapping("/my")
//    @SecurityRequirement(name = SECURITY_BEARER)
//    @Operation(description = "Busca todos os alugueis de veículos feitos pelo usuário atual.")
//    public ResponseEntity findAllMyRents() {
//
//    }
}
