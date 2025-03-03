package io.api.carrent.domain.dto.output;

import io.api.carrent.domain.enums.RentStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VehicleRentDetailDTO {
    private Long id;
    private String plate;
    private String brand;
    private String model;
    private String vehicleType;
    private RentStatus status;
    private String renter;
    private String withdrawCode;
    private String returnCode;
    private Instant withdrawDatetime;
    private Instant returnDatetime;
    private String operator;
    private String returnOperator;
    private Instant expirationWithdrawDate;
    private String cancellationReason;
    private Instant createdAt;

    public String getStatus() {
        return this.status.getDescription();
    }

    public void setStatus(String status) {
        this.status = RentStatus.valueOf(status);
    }
}
