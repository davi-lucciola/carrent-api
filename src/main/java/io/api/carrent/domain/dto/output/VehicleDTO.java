package io.api.carrent.domain.dto.output;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.api.carrent.domain.enums.VehicleStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VehicleDTO {
    private Long id;
    private String plate;
    private String brand;
    private String model;
    private Integer year;
    private Float odometer;
    private VehicleStatus status;
    private VehicleTypeDTO vehicleType;
    private Boolean flActive;
    private Instant createdAt;

    public void setStatus(String status) {
        this.status = VehicleStatus.valueOf(status);
    }

    public void setVehicleType(String json) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        this.vehicleType = objectMapper.readValue(json, VehicleTypeDTO.class);
    }
}
