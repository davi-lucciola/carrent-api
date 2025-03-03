package io.api.carrent.domain.dto.output;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.api.carrent.domain.enums.RentStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VehicleRentDTO {
    private Long id;
    private String plate;
    private String brand;
    private String model;
    private String vehicleType;
    private RentStatus status;
    private String renter;
    private Instant withdrawDatetime;
    private Instant returnDatetime;
    private String operator;
    private String returnOperator;
    private Instant expirationWithdrawDate;
    private Instant createdAt;

    @JsonIgnore
    private Integer total;

    public String getStatus() {
        return this.status.getDescription();
    }

    public void setStatus(String status) {
        this.status = RentStatus.valueOf(status);
    }
}
