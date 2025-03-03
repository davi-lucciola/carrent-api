package io.api.carrent.domain.entities;

import io.api.carrent.domain.enums.RentStatus;
import io.api.carrent.domain.enums.VehicleStatus;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;

@Data
@Entity
@ToString
@NoArgsConstructor
@Table(name = "vehicle_status_historical")
public class VehicleStatusHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "vehicle_id", referencedColumnName = "id", nullable = false)
    private Vehicle vehicle;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private VehicleStatus status;

    @Column(name = "rent_status")
    @Enumerated(EnumType.STRING)
    private RentStatus rentStatus;

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt = LocalDateTime.now();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "rent_id", referencedColumnName = "id")
    private VehicleRent rent;

    public VehicleStatusHistory(Vehicle vehicle) {
        this.vehicle = vehicle;
        this.status = vehicle.getStatus();
    }

    public VehicleStatusHistory(Vehicle vehicle, VehicleRent rent) {
        this.rent = rent;
        this.vehicle = vehicle;
        this.status = vehicle.getStatus();
        this.rentStatus = rent.getStatus();
    }
}
