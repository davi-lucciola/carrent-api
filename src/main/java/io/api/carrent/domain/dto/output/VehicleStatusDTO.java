package io.api.carrent.domain.dto.output;


import com.fasterxml.jackson.annotation.JsonIgnore;
import io.api.carrent.domain.enums.RentStatus;
import io.api.carrent.domain.enums.VehicleStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VehicleStatusDTO {
    private Long id;
    private VehicleStatus status;
    private String renter;
    private RentStatus rentStatus;
    private Instant updatedAt;

    @JsonIgnore
    private Integer total;

    public String getStatus() {
        return this.status.getDescription();
    }

    public void setStatus(String status) {
        this.status = VehicleStatus.valueOf(status);
    }

    public String getRentStatus() {
        return this.rentStatus != null ? this.rentStatus.getDescription() : null;
    }

    public void setRentStatus(String rentStatus) {
        this.rentStatus = rentStatus != null ? RentStatus.valueOf(rentStatus) : null;
    }
}
